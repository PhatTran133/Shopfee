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
                .Include(c => c.CartToppingDrinks)
                .ThenInclude(ct => ct.ToppingDrink)
                .ThenInclude(d => d.Drink)
                .Include(c => c.CartToppingDrinks)
                .ThenInclude(ct => ct.ToppingDrink)
                .ThenInclude(td => td.Topping)
                .FirstOrDefaultAsync(x => x.Id == cartId);
        }

        public async Task<Cart?> GetCartByUserIdAsync(int userId)
        {
            return await _context.Carts
                .Include(c => c.CartToppingDrinks)
                .ThenInclude(ct => ct.ToppingDrink)
                .ThenInclude(d => d.Drink)
                .Include(c => c.CartToppingDrinks)
                .ThenInclude(ct => ct.ToppingDrink)
                .ThenInclude(t => t.Topping)
                .FirstOrDefaultAsync(x => x.UserId == userId);
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
