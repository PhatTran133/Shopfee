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
        Task<List<Cart>> GetCartsByUserId(int userId);
        Task<Cart> GetCartByIdAsync(int cartId);
        Task<Cart?> GetCartByUserIdAsync(int userId);
        Task CreateCartAsync(Cart cart);
        Task UpdateCartAsync(Cart cart);
        Task RemoveCartAsync(Cart cart);
    }
}