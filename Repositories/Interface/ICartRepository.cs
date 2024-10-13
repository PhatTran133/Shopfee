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
        Task<Cart> GetCartByIdAsync(int cartId);
        Task<IEnumerable<Cart?>> GetCartsByUserIdAsync(int userId);
        Task CreateCartAsync(Cart cart);
        Task UpdateCartAsync(Cart cart);
    }
}