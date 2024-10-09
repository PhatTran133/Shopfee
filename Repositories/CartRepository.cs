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
        public async Task AddToCartAsync(CartItem cartItem)
        {
            _context.CartItems.Add(cartItem);
            await _context.SaveChangesAsync();
        }

        public async Task CreateCart(Cart cart)
        {
            _context.Carts.Add(cart);
            await _context.SaveChangesAsync();
        }

        public async Task DeleteCartItemAsync(CartItem cartItem)
        {
            _context.CartItems.Remove(cartItem);
            await _context.SaveChangesAsync();
        }

        public async Task<IEnumerable<Cart>> GetAllCartsAsync()
        {
            return await _context.Carts
                .Include(c => c.CartItems)
                .ThenInclude(d => d.Drink)
                .ThenInclude(c => c.Category)
                .ToListAsync();
        }

        public async Task<Cart?> GetCartByIdAsync(int cartId)
        {
            return await _context.Carts.SingleOrDefaultAsync(x => x.Id == cartId);
        }

        public async Task<CartItem?> GetCartItemExistingAsync(int cartId, int drinkId)
        {
            return await _context.CartItems.Where(x => x.CartId == cartId && x.DrinkId ==drinkId).FirstOrDefaultAsync();
        }

        public async Task UpdateCart(Cart cart)
        {
            _context.Carts.Update(cart);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateCartItemAsync(CartItem cartItem)
        {
            _context.CartItems.Update(cartItem);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateTotalPriceOfCart(int cartId, decimal? priceUpdated)
        {
            var cart = await _context.Carts
              .Include(c => c.CartItems)
              .FirstOrDefaultAsync(c => c.Id == cartId);
            if (priceUpdated == null)
            {
                cart.TotalPrice = cart.CartItems.Sum(ci => ci.TotalPrice);
            }
            else
            {
                cart.TotalPrice += priceUpdated;
            }

            _context.Carts.Update(cart);
            await _context.SaveChangesAsync();
        }
    }
}
