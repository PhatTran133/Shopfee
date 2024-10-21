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
    public class CartToppingDrinkRepository : ICartToppingDrinkRepository
    {
        private readonly CoffeeShopContext _context;
        public CartToppingDrinkRepository()
        {
            _context = new CoffeeShopContext();
        }
        public async Task AddCartItemAsync(CartToppingDrink cartToppingDrink)
        {
            _context.CartToppingDrinks.Add(cartToppingDrink);
            await _context.SaveChangesAsync();
        }

        public async Task<CartToppingDrink?> GetCartToppingDrinkByCartIdAsync(int cartId, int drinkToppingId)
        {
            return await _context.CartToppingDrinks.Include(c => c.Cart)
                .FirstOrDefaultAsync(x => x.CartId == cartId && x.ToppingDrinkId == drinkToppingId);
        }

        public async Task<CartToppingDrink?> GetCartToppingDrinkByIdAsync(int id)
        {
            return await _context.CartToppingDrinks.FirstOrDefaultAsync(x => x.Id == id);
        }

        public async Task RemoveCartItemAsync(CartToppingDrink cartToppingDrink)
        {
            _context.CartToppingDrinks.Remove(cartToppingDrink);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateCartItemAsync(CartToppingDrink cartToppingDrink)
        {
            _context.CartToppingDrinks.Update(cartToppingDrink);
            await _context.SaveChangesAsync();
        }

        public async Task<int> CalculateTotal(int cartId)
        {
            return await _context.CartToppingDrinks
                  .Where(ctd => ctd.CartId == cartId)
                  .Join(
                      _context.DrinkToppings,
                      cartToppingDrink => cartToppingDrink.ToppingDrinkId,
                      drinkTopping => drinkTopping.Id,
                      (cartToppingDrink, drinkTopping) => new
                      {
                          CartToppingDrink = cartToppingDrink,
                          DrinkId = drinkTopping.DrinkId
                      }
                  )
                  .GroupBy(x => x.DrinkId)
                  .Select(g => g.First().CartToppingDrink.Total)
                  .SumAsync();
        }
    }
}
