using BussinessObjects.Models;
using Microsoft.EntityFrameworkCore;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class CartRepository : ICartRepository
    {
        private readonly CoffeeShopContext _context;
        public CartRepository()
        {
            _context = new CoffeeShopContext();
        }

        public async Task CreateCartAsync(Cart cart)
        {
            _context.Carts.Add(cart);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateCartAsync(Cart cart)
        {
            _context.Carts.Update(cart);
            await _context.SaveChangesAsync();
        }

        public async Task<Cart?> GetCartByIdAsync(int cartId)
        {
            return await _context.Carts
                .FirstOrDefaultAsync(x => x.Id == cartId);
        }

        public async Task<Cart?> GetCartByUserIdAsync(int userId)
        {
            return await _context.Carts
                .Include(c => c.User) 
                .Include(c => c.CartItems)
                    .ThenInclude(ci => ci.Drink) 
                .Include(c => c.CartItems)
                    .ThenInclude(ci => ci.CartItemToppings) 
                    .ThenInclude(cit => cit.Topping) 
                .Where(c => c.UserId == userId)
                .FirstOrDefaultAsync();
        }

        public async Task RemoveCartAsync(Cart cart)
        {
            _context.Carts.Remove(cart);
            await _context.SaveChangesAsync();
        }

        public async Task<List<Cart>> GetCartsByUserId(int userId)
        {
            return await _context.Carts.Where(x => x.UserId == userId).ToListAsync();
        }
    }
}
