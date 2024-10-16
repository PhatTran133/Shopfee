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
                .Include(u => u.User)
                .Include(otd => otd.OrderToppingDrinks)
                    .ThenInclude(td => td.ToppingDrink)
                    .ThenInclude(d => d.Drink)
                .Include(otd => otd.OrderToppingDrinks)
                    .ThenInclude(td => td.ToppingDrink)
                    .ThenInclude(t => t.Topping)
                .Where(u => u.UserId == userId)
                .Where(x => x.StatusOfOder == status)
                .Where(p => paymentCreated ? p.Payments.Any() : !p.Payments.Any()) // Check if payments exist or not based on paymentCreated
                .ToListAsync();
        }

        public async Task UpdateOrder(TblOrder tblOrder)
        {
            _context.Update(tblOrder);
            await _context.SaveChangesAsync();
        }
    }
}
