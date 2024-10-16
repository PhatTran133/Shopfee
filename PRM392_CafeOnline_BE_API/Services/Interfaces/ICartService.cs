using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface ICartService
    {
        public Task<CartToppingDrinkDTO> AddToCart(AddToCartRequestDTO requestDTO);
        public Task RemoveFromCart (int cartItemId);
        public Task<IEnumerable<CartDTO>> GetAllCartsByUserId(int userId);
        public Task<CartDTO> GetCartById(int cartId);
        public Task<CartToppingDrinkDTO> UpdateCartItem(int cartToppingDrinkId, UpdateCartItemRequestDTO updateCartItemRequestDTO);
    }
}
