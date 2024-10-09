using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface ICartRepository
    {
        Task<IEnumerable<Cart>> GetAllCartsAsync();
        Task<Cart?> GetCartByIdAsync(int cartId);
        Task CreateCart(Cart cart);
        Task UpdateCart(Cart cart);
        Task<CartItem?> GetCartItemExistingAsync(int cartId, int drinkId);
        Task AddToCartAsync(CartItem cartItem);
        Task UpdateCartItemAsync(CartItem cartItem);
        Task DeleteCartItemAsync(CartItem cartItem);
        Task UpdateTotalPriceOfCart(int cartId);
    }
}
