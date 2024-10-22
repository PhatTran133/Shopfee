using BussinessObjects.Models;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class CartItemToppingRepository : ICartItemToppingRepository
    {
        private readonly CoffeeShopContext _context;
        public CartItemToppingRepository()
        {
            _context = new CoffeeShopContext();
        }
        public async Task AddCartItemToppingAsync(CartItemTopping cartItemTopping)
        {
            _context.CartItemToppings.Add(cartItemTopping);
            await _context.SaveChangesAsync();
        }
    }
}
