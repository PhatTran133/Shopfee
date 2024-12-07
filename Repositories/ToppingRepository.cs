using BussinessObjects.DTO;
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
    public class ToppingRepository : IToppingRepository
    {
        private readonly CoffeeShopContext _context;
        public ToppingRepository()
        {
            _context = new CoffeeShopContext();
        }

        public async Task<List<Topping>> GetAllTopping()
        {
            return await _context.Toppings.ToListAsync();
        }

        public async Task<Topping?> GetToppingByIdAsync(int id)
        {
            return await _context.Toppings.FirstOrDefaultAsync(x => x.Id == id);
        }
    }
}
