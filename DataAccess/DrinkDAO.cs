using BussinessObjects.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess
{
    public class DrinkDAO
    {
        private readonly CoffeeShopContext _context;
        private static DrinkDAO instance = null;

        public DrinkDAO()
        {
            _context = new CoffeeShopContext();
        }

        public static DrinkDAO Instance
        {
            get
            {
                if (instance == null)
                {
                    return new DrinkDAO();
                }
                return instance;
            }
        }

        public async Task<List<Drink>> SearchDrinksByNameAsync(string name)
        {
            return await _context.Drinks
                .Include(d => d.Category)
                .Where(d => d.Name.Contains(name) && d.IsDeleted == false)
                .ToListAsync();
        }
    }
}
