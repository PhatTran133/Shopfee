using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface ICartItemRepository
    {
        Task AddCartItemAsync(CartItem cartItem);
        Task<CartItem?> GetCartItemAsync(int id);    
        Task DeleteCartItemAsync(CartItem cartItem);
        Task UpdateCartItemAsync(CartItem cartItem);
    }
}
