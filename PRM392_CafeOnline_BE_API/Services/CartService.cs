//using BussinessObjects.DTO;
//using BussinessObjects.Models;
//using PRM392_CafeOnline_BE_API.Services.Interfaces;
//using Repositories.Interface;
//using System.Security.Claims;

//namespace PRM392_CafeOnline_BE_API.Services
//{
//    public class CartService : ICartService
//    {
//        private readonly ICartRepository _cartRepository;
//        private readonly IUserRepository _userRepository;
//        private readonly IDrinkRepository _drinkRepository;
//        private readonly IHttpContextAccessor _httpContextAccessor;
//        public CartService(ICartRepository cartRepository, IHttpContextAccessor httpContextAccessor, IUserRepository userRepository, IDrinkRepository drinkRepository)
//        {
//            _cartRepository = cartRepository;
//            _httpContextAccessor = httpContextAccessor;
//            _userRepository = userRepository;
//            _drinkRepository = drinkRepository;
//        }

//        public async Task AddToCartAsync(int cartId, CartItemDTO cartItemDTO)
//        {
//            var drinkFound = await _drinkRepository.GetDrinkByIdAsync(cartItemDTO.DrinkId)
//                ?? throw new Exception("Drink not found");

//            if (cartId == 0)
//            {
//                // Create a new cart
//                Cart cart;
//                var emailClaim = _httpContextAccessor.HttpContext?.User.FindFirst(ClaimTypes.Email);

//                if (emailClaim == null)
//                {
//                    // Create cart for anonymous user
//                    cart = new Cart
//                    {
//                        CreatedDate = DateTime.Now,
//                        UpdatedDate = DateTime.Now
//                    };
//                    await _cartRepository.CreateCart(cart);
//                }
//                else
//                {
//                    // Create cart for authenticated user
//                    var email = emailClaim.Value;
//                    var user = await _userRepository.GetByEmail(email)
//                        ?? throw new Exception("User not found");

//                    cart = new Cart
//                    {
//                        User = user,
//                        UserId = user.Id,
//                        CreatedDate = DateTime.Now,
//                        UpdatedDate = DateTime.Now
//                    };
//                    await _cartRepository.CreateCart(cart);
//                }

//                cartId = cart.Id;  // Use the new cartId
//            }

//            // Check if the cart exists
//            var cartExist = await _cartRepository.GetCartByIdAsync(cartId);
//            if (cartExist == null)
//            {
//                throw new Exception("Cart not found");
//            }

//            // Check if the CartItem for the same Drink already exists
//            var existingCartItem = await _cartRepository.GetCartItemExistingAsync(cartId, cartItemDTO.DrinkId);

//            if (existingCartItem != null)
//            {
//                // If it exists, update the quantity
//                existingCartItem.Quantity += cartItemDTO.Quantity;
//                cartExist.UpdatedDate = DateTime.Now;

//                await _cartRepository.UpdateCart(cartExist);
//                await _cartRepository.UpdateCartItemAsync(existingCartItem);
//            }
//            else
//            {
//                // If it does not exist, create a new CartItem
//                var newCartItem = new CartItem
//                {
//                    CartId = cartId,
//                    DrinkId = cartItemDTO.DrinkId,
//                    Quantity = cartItemDTO.Quantity,
//                    Price = drinkFound.Price.Value
//                };

//                cartExist.UpdatedDate = DateTime.Now;

//                await _cartRepository.UpdateCart(cartExist);
//                await _cartRepository.AddToCartAsync(newCartItem);
//            }
//            await _cartRepository.UpdateTotalPriceOfCart(cartId);
//        }


//        public async Task DeleteCartItemAsync(int cartId, int drinkId)
//        {
//            var cartItem = await _cartRepository.GetCartItemExistingAsync(cartId, drinkId) ?? throw new Exception("cartItem not found");
//            await _cartRepository.DeleteCartItemAsync(cartItem);
//            await _cartRepository.UpdateTotalPriceOfCart(cartId);
//        }

//        public async Task<IEnumerable<CartDTO>> GetCartDTOsAsync()
//        {
//            var carts = await _cartRepository.GetAllCartsAsync();
//            var cartDTOs = new List<CartDTO>();
            
//            foreach(var cart in carts)
//            {
//                var cartItemDTOs = cart.CartItems.Select(cartItem => new CartItemResponseDTO
//                {
//                    Quantity = cartItem.Quantity,
//                    TotalPrice = cartItem.TotalPrice,
//                    Drink = new DrinkDTO
//                    {
//                        Id = cartItem.DrinkId,
//                        Name = cartItem.Drink.Name,
//                        Image = cartItem.Drink.Image,
//                        CategoryName = cartItem.Drink.Category.Name,
//                        CreatedDate = cartItem.Drink.CreatedDate,
//                        Description = cartItem.Drink.Description,
//                        Price = cartItem.Drink.Price,
//                        Size = cartItem.Drink.Size,
//                        UpdatedDate = cartItem.Drink.UpdatedDate
//                    }
//                }).ToList();

//                var cartDTO = new CartDTO
//                {
//                    Id = cart.Id,
//                    CreatedDate = cart.CreatedDate,
//                    UpdatedDate = cart.UpdatedDate,
//                    CartItemResponseDTO = cartItemDTOs,
//                    TotalPrice = cart.TotalPrice,
//                    UserId = cart.UserId
//                };

//                cartDTOs.Add(cartDTO);
//            }

//            return cartDTOs;
//        }

//        public async Task<CartItemDTO> UpdateCartItemQuantityAsync(int cartId, int drinkId, int quantity)
//        {
//            var cartItem = await _cartRepository.GetCartItemExistingAsync(cartId, drinkId);
//            if (cartItem == null)
//            {
//                throw new Exception("cartItem not found");
//            }
//            cartItem.Quantity = quantity;
//            await _cartRepository.UpdateCartItemAsync(cartItem);

//            await _cartRepository.UpdateTotalPriceOfCart(cartId);
//            return new CartItemDTO
//            {
//                DrinkId = cartItem.DrinkId,
//                Quantity = cartItem.Quantity
//            };
//        }
//    }
//}