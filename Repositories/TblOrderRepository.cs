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
    public class TblOrderRepository : IOrderRepository
    {
        private readonly CoffeeShopContext _context;
        public TblOrderRepository()
        {
            _context = new CoffeeShopContext();
        }
        public async Task CreateOrder(TblOrder tblOrder)
        {
            _context.Add(tblOrder);
            await _context.SaveChangesAsync();
        }

        public async Task<IEnumerable<TblOrder>> GetOrdersFilterByStatus(bool status, bool paymentCreated, int userId)
        {
            return await _context.TblOrders
                .Include(o => o.User)
                .Include(o => o.OrderItems)
                    .ThenInclude(oi => oi.Drink)
                .Include(o => o.OrderItems) // Start from OrderItems
                    .ThenInclude(oi => oi.OrderItemToppings) // Navigate to OrderItemToppings
                    .ThenInclude(oit => oit.Topping) // Navigate to Topping from OrderItemToppings
                .Where(o => o.UserId == userId && o.StatusOfOder == status)
                .Where(o => paymentCreated ? o.Payments.Any() : !o.Payments.Any())
                .ToListAsync();
        }

        public async Task UpdateOrder(TblOrder tblOrder)
        {
            _context.Update(tblOrder);
            await _context.SaveChangesAsync();
        }
    }
}
