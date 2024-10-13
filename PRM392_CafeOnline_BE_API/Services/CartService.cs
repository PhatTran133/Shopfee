using AutoMapper;
using BussinessObjects.DTO;
using BussinessObjects.Models;
using PRM392_CafeOnline_BE_API.Services.Enums;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class CartService : ICartService
    {
        private readonly ICartRepository _cartRepository;
        private readonly ICartToppingDrinkRepository _cartToppingDrinkRepository;
        private readonly IDrinkToppingRepository _drinkToppingRepository;
        private readonly IDrinkRepository _drinkRepository;
        private readonly IToppingRepository _toppingRepository;
        private readonly IMapper _mapper;
        public CartService(ICartRepository cartRepository, IMapper mapper, ICartToppingDrinkRepository cartToppingDrinkRepository,
            IDrinkToppingRepository drinkToppingRepository, IDrinkRepository drinkRepository, IToppingRepository toppingRepository)
        {
            _cartRepository = cartRepository;
            _mapper = mapper;
            _cartToppingDrinkRepository = cartToppingDrinkRepository;
            _drinkToppingRepository = drinkToppingRepository;
            _toppingRepository = toppingRepository;
            _drinkRepository = drinkRepository;
        }

        public async Task<CartToppingDrinkDTO> AddToCart(ToppingDrinkRequestDTO requestDTO)
        {
            try
            {
                Cart cart;
                cart = await _cartRepository.GetCartByIdAsync(requestDTO.CartId);
                if (cart == null || requestDTO.CartId == 0)
                {
                    cart = new Cart()
                    {
                        CreatedDate = DateTime.Now,
                        UpdatedDate = DateTime.Now,
                        UserId = requestDTO.UserId,
                    };

                    await _cartRepository.CreateCartAsync(cart);
                    requestDTO.CartId = cart.Id;
                }
                var cartToppingDrinkExist = await _cartToppingDrinkRepository.GetCartToppingDrinkByCartIdAsync(requestDTO.CartId, requestDTO.ToppingDrinkId);
                if (cartToppingDrinkExist != null)
                {
                    await CalculateTotalPrice(cartToppingDrinkExist, requestDTO.Quantity, requestDTO.CartId, OperationEnums.ADD);

                    cartToppingDrinkExist.Quantity += requestDTO.Quantity;

                    await _cartToppingDrinkRepository.UpdateCartItemAsync(cartToppingDrinkExist);

                    return _mapper.Map<CartToppingDrinkDTO>(cartToppingDrinkExist);
                }
                var cartToppingDrink = _mapper.Map<CartToppingDrink>(requestDTO);
                await _cartToppingDrinkRepository.AddCartItemAsync(cartToppingDrink);
                await CalculateTotalPrice(cartToppingDrink, requestDTO.Quantity, cart.Id, OperationEnums.ADD);

                return _mapper.Map<CartToppingDrinkDTO>(cartToppingDrink);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        private async Task CalculateTotalPrice(CartToppingDrink cartToppingDrink, int quantity, int cartId, OperationEnums operationEnums)
        {
            var cart = await _cartRepository.GetCartByIdAsync((int)cartId);
            if(cart.CartToppingDrinks.Count == 0)
            {
                cart.TotalPrice = 0;
                await _cartRepository.UpdateCartAsync(cart);
                return;
            }

            var drinkTopping = await _drinkToppingRepository.FindByIdAsync((int)cartToppingDrink.ToppingDrinkId);

            var drink = await _drinkRepository.GetDrinkByIdAsync((int)drinkTopping.DrinkId);

            var topping = await _toppingRepository.GetToppingByIdAsync((int)drinkTopping.ToppingId);

            var price = (drink.Price + topping.Price) * quantity;

            switch (operationEnums)
            {
                case OperationEnums.ADD:
                    cart.TotalPrice = cart.TotalPrice.HasValue ? cart.TotalPrice + price : price;
                    break;
                case OperationEnums.SUBSTRACT:
                    cart.TotalPrice = cart.TotalPrice.HasValue ? cart.TotalPrice - price : price;
                    break;
                default:
                    throw new ArgumentException("Invalid price operation");
            }

            await _cartRepository.UpdateCartAsync(cart);

            cartToppingDrink.ToppingDrink = drinkTopping;
            cartToppingDrink.ToppingDrink.Drink = drink;
            cartToppingDrink.ToppingDrink.Topping = topping;
        }

        public async Task<IEnumerable<CartDTO>> GetAllCartsByUserId(int userId)
        {
            var carts = await _cartRepository.GetCartsByUserIdAsync(userId);
            return _mapper.Map<List<CartDTO>>(carts);
        }

        public async Task<CartDTO> GetCartById(int cartId)
        {
            var cart = await _cartRepository.GetCartByIdAsync(cartId);
            return _mapper.Map<CartDTO>(cart);
        }

        public async Task RemoveFromCart(int cartItemId)
        {
            var cartItem = await _cartToppingDrinkRepository.GetCartToppingDrinkByIdAsync(cartItemId);
            if (cartItem == null)
            {
                throw new Exception("Item not found");
            }
            await CalculateTotalPrice(cartItem, (int)cartItem.Quantity, (int)cartItem.CartId, OperationEnums.SUBSTRACT);
            await _cartToppingDrinkRepository.RemoveCartItemAsync(cartItem);
        }

        public async Task<CartToppingDrinkDTO> UpdateQuantity(int cartToppingDrinkId, int newQuantity)
        {
            var cartItem = await _cartToppingDrinkRepository.GetCartToppingDrinkByIdAsync(cartToppingDrinkId);
            if (cartItem == null)
            {
                throw new Exception("Item not found");
            }

            if(newQuantity == 0)
            {
                await RemoveFromCart(cartItem.Id);
                return new CartToppingDrinkDTO
                {
                    Quantity = 0
                };
            }

            // Calculate the difference between the new quantity and the current quantity
            int quantityDifference = newQuantity - cartItem.Quantity.Value;

            // If the quantity is being increased
            if (quantityDifference > 0)
            {
                // Calculate the total price change based on the difference in quantity
                await CalculateTotalPrice(cartItem, quantityDifference, (int)cartItem.CartId, OperationEnums.ADD);
            }
            // If the quantity is being decreased
            else if (quantityDifference < 0)
            {
                // Calculate the total price change based on the difference in quantity (make it positive for subtraction)
                await CalculateTotalPrice(cartItem, -quantityDifference, (int)cartItem.CartId, OperationEnums.SUBSTRACT);
            }

            // Update the cart item quantity
            cartItem.Quantity = newQuantity;

            // Update the cart item in the repository
            await _cartToppingDrinkRepository.UpdateCartItemAsync(cartItem);

            // Return the updated cart item
            return _mapper.Map<CartToppingDrinkDTO>(cartItem);
        }

    }
}