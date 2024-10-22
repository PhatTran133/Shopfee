using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface ICartService
    {
        public Task<CartItemDTO> AddToCart(AddToCartRequestDTO requestDTO);
        public Task RemoveFromCart (int cartItemId);
        public Task<CartDTO> GetCartByUserId(int userId);
        public Task<CartDTO> GetCartById(int cartId);
        public Task<CartItemDTO> UpdateCartItem(int cartToppingDrinkId, UpdateCartItemRequestDTO updateCartItemRequestDTO);
    }
}
