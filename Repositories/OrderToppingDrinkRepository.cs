using BussinessObjects.Models;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class OrderToppingDrinkRepository : IOrderToppingDrinkRepository
    {
        private readonly CoffeeShopContext _context;
        public OrderToppingDrinkRepository()
        {
            _context = new CoffeeShopContext();
        }

        public async Task CreateOrderToppingDrinks(IEnumerable<OrderToppingDrink> orderToppingDrinks)
        {
            await _context.AddRangeAsync(orderToppingDrinks);
            await _context.SaveChangesAsync();
        }

        public async Task CreateOrderToppingDrink(OrderToppingDrink orderToppingDrink)
        {
            await _context.AddAsync(orderToppingDrink);
            await _context.SaveChangesAsync();
        }
    }
}
