using BussinessObjects.Models;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class OrderItemRepository : IOrderItemRepository
    {
        private readonly CoffeeShopContext _context;
        public OrderItemRepository()
        {
            _context = new CoffeeShopContext();
        }
        public async Task AddOrderItemAsync(OrderItem orderItem)
        {
            _context.OrderItems.Add(orderItem);
            await _context.SaveChangesAsync();
        }
    }
}
