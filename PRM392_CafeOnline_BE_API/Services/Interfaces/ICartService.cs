using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface ICartService
    {
        Task<IEnumerable<CartDTO>> GetCartDTOsAsync();
        Task AddToCartAsync(int cartId, CartItemDTO cartItem);
        Task<CartItemDTO> UpdateCartItemQuantityAsync(int cartId, int drinkId, int quantity);
        Task DeleteCartItemAsync(int cartId, int drinkId);
    }
}
