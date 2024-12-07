using BussinessObjects.Models;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class OrderItemToppingRepository : IOrderItemToppingRepository
    {
        private readonly CoffeeShopContext _context;
        public OrderItemToppingRepository()
        {
            _context = new CoffeeShopContext();
        }
        public async Task AddOrderItemToppingAsync(OrderItemTopping orderItemTopping)
        {
            _context.OrderItemToppings.Add(orderItemTopping);
            await _context.SaveChangesAsync();
        }
    }
}
