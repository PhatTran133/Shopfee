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
        private readonly IDrinkRepository _drinkRepository;
        private readonly IToppingRepository _toppingRepository;
        private readonly ICartItemRepository _cartItemRepository;
        private readonly ICartItemToppingRepository _cartItemToppingRepository;
        private readonly IUserRepository _userRepository;
        private readonly IMapper _mapper;
        public CartService(ICartRepository cartRepository, IMapper mapper,
            IDrinkRepository drinkRepository, IToppingRepository toppingRepository,
            ICartItemRepository cartItemRepository, ICartItemToppingRepository cartItemToppingRepository, IUserRepository userRepository)
        {
            _cartRepository = cartRepository;
            _mapper = mapper;
            _toppingRepository = toppingRepository;
            _drinkRepository = drinkRepository;
            _cartItemRepository = cartItemRepository;
            _cartItemToppingRepository = cartItemToppingRepository;
            _userRepository = userRepository;
        }

        public async Task<CartItemDTO> AddToCart(AddToCartRequestDTO requestDTO)
        {
            try
            {
                var user = await _userRepository.GetTblUser(requestDTO.UserId);
                if (user == null)
                {
                    throw new Exception("Invalid credentials");
                }
                Cart? cart;
                CartItem newCartItem = new();
                cart = await _cartRepository.GetCartByUserIdAsync(requestDTO.UserId);
                if (cart == null)
                {
                    cart = new Cart
                    {
                        UserId = requestDTO.UserId,
                        CreatedDate = DateTime.Now,
                        UpdatedDate = DateTime.Now
                    };
                    await _cartRepository.CreateCartAsync(cart);
                }

                var existingDrink = await _drinkRepository.GetDrinkByIdAsync(requestDTO.DrinkId) ?? throw new Exception("Drink not found");

                var existingCartItem = await _cartItemRepository.GetCartItemByDrinkIdAsync(cart.Id, requestDTO.DrinkId);

                if (existingCartItem != null)
                {
                    await _cartItemRepository.DeleteCartItemAsync(existingCartItem);
                }

                var newCartItemDTO = _mapper.Map<CartItemDTO>(requestDTO);
                newCartItem = _mapper.Map<CartItem>(newCartItemDTO);
                newCartItem.CartId = cart.Id;
                newCartItem.DrinkId = existingDrink.Id;
                await _cartItemRepository.AddCartItemAsync(newCartItem);

                if (requestDTO.Toppings != null)
                {
                    foreach (var topping in requestDTO.Toppings)
                    {

                        CartItemTopping cartItemTopping;
                        cartItemTopping = new()
                        {
                            CartItemId = newCartItem.Id,
                            ToppingId = topping.Id,
                        };
                        await _cartItemToppingRepository.AddCartItemToppingAsync(cartItemTopping);
                    }
                }
                var cartItemAdded = await _cartItemRepository.GetCartItemAsync(newCartItem.Id);
                return _mapper.Map<CartItemDTO>(cartItemAdded);
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public async Task<CartDTO> GetCartByUserId(int userId)
        {
            var cart = await _cartRepository.GetCartByUserIdAsync(userId);
            return _mapper.Map<CartDTO>(cart);
        }

        public async Task<CartDTO> GetCartById(int cartId)
        {
            try
            {
                var cart = await _cartRepository.GetCartByIdAsync(cartId);
                return _mapper.Map<CartDTO>(cart);
            }
            catch (Exception ex)
            {
                throw new Exception("Cart not found");
            }
        }

        public async Task RemoveFromCart(int cartItemId)
        {
            try
            {
                var cartItem = await _cartItemRepository.GetCartItemAsync(cartItemId);
                if (cartItem == null)
                {
                    throw new Exception("Item not found");
                }
                await _cartItemRepository.DeleteCartItemAsync(cartItem);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public async Task<CartItemDTO> UpdateCartItem(int cartItemId, UpdateCartItemRequestDTO updateCartItemRequestDTO)
        {
            try
            {
                var existCartItem = await _cartItemRepository.GetCartItemAsync(cartItemId);
                if (existCartItem == null)
                {
                    throw new Exception("Cart item not found");
                }
                var cartToppingDrinkUpdate = _mapper.Map(updateCartItemRequestDTO, existCartItem);

                await _cartItemRepository.UpdateCartItemAsync(cartToppingDrinkUpdate);

                return _mapper.Map<CartItemDTO>(cartToppingDrinkUpdate);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
    }
}