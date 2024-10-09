using BussinessObjects.DTO;
using DataAccess;
using DataAccess.Models;
using Microsoft.EntityFrameworkCore;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class DrinkRepository : IDrinkRepository
    {
        private readonly CoffeeShopContext _context;
        public DrinkRepository(CoffeeShopContext context)
        {
            _context = context;
        }
        public Task<List<Drink>> SearchDrinksByIdAsync(int id) => DrinkDAO.Instance.SearchDrinksByIdAsync(id);
        public Task<List<Drink>> FilterDrinksAsync(string? name, string? categoryName, decimal? minPrice, decimal? maxPrice, DateTime? startDate, DateTime? endDate, string? size) => DrinkDAO.Instance.FilterDrinksAsync(name, categoryName, minPrice, maxPrice, startDate, endDate, size);
        public async Task<List<DrinkDTO>> GetDrinksByCategoryAsync(string categoryName)
        {
            var normalizedCategoryName = categoryName.Trim().ToLower();

            var result = await (from d in _context.Drinks
                                join c in _context.Categories on d.CategoryId equals c.Id
                                where c.Name.Trim().ToLower() == normalizedCategoryName
                                select new DrinkDTO
                                {
                                    Id = d.Id,
                                    Name = d.Name,
                                    CategoryName = c.Name,
                                    Description = d.Description,
                                    Price = d.Price,
                                    Size = d.Size,
                                    Image = d.Image,
                                    CreatedDate = d.CreatedDate,
                                    UpdatedDate = d.UpdatedDate,
                                }).ToListAsync();

            return result;
        }

        public async Task<DrinkDetailDTO> GetDrinkDetailAsync(int drinkId)
        {
            var drinkDetail = await _context.Drinks
       .Include(d => d.Category) // JOIN với bảng Categories
       .Include(d => d.DrinkToppings) // JOIN với bảng DrinkToppings
           .ThenInclude(dt => dt.Topping) // JOIN với bảng Toppings
       .Where(d => d.Id == drinkId)
       .Select(d => new DrinkDetailDTO
       {
           Id = d.Id,
           Name = d.Name,
           CategoryName = d.Category.Name,
           Description = d.Description,
           Price = d.Price,
           Size = d.Size,
           Image = d.Image,
           CreatedDate = d.CreatedDate,
           UpdatedDate = d.UpdatedDate,
           ToppingNames = d.DrinkToppings.Select(dt => dt.Topping.Name).ToList() // Lấy danh sách tên toppings
       })
       .FirstOrDefaultAsync(); // Lấy một đối tượng duy nhất

            return drinkDetail;

          
        }

    }
}
