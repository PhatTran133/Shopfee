using BussinessObjects.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Drawing;
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

        public async Task<List<Drink>> SearchDrinksByIdAsync(int id)
        {
            return await _context.Drinks
                .Include(d => d.Category)
                .Where(d => d.Id == id && d.IsDeleted == false)
                .ToListAsync();
        }

        public async Task<Drink?> GetDrinkByIdAsync(int id)
        {
            return await _context.Drinks.FirstOrDefaultAsync(x => x.Id == id);
        }

        public async Task<List<Drink>> FilterDrinksAsync(string? name, string? categoryName, decimal? minPrice, decimal? maxPrice, DateTime? startDate, DateTime? endDate, string? size)
        {
            try
            {
                var query = _context.Drinks.Include(d => d.Category).AsQueryable();

                // Lọc theo Drink Name
                if (!string.IsNullOrEmpty(name))
                {
                    query = query.Where(d => d.Name.Contains(name) && d.IsDeleted == false);
                }

                // Lọc theo Category Name
                if (!string.IsNullOrEmpty(categoryName))
                {
                    query = query.Where(d => d.Category != null && d.Category.Name.Contains(categoryName));
                }

                // Lọc theo khoảng giá
                if (minPrice.HasValue)
                {
                    query = query.Where(d => d.Price >= minPrice);
                }

                if (maxPrice.HasValue)
                {
                    query = query.Where(d => d.Price <= maxPrice);
                }

                // Lọc theo khoảng ngày tạo
                if (startDate.HasValue)
                {
                    query = query.Where(d => d.CreatedDate >= startDate);
                }

                if (endDate.HasValue)
                {
                    query = query.Where(d => d.CreatedDate <= endDate);
                }

                // Lọc theo kích thước (Size)
                if (!string.IsNullOrEmpty(size))
                {
                    query = query.Where(d => d.Size == size);
                }

                // Thực hiện truy vấn bất đồng bộ và trả về kết quả
                return await query.ToListAsync();
            }
            catch (Exception ex)
            {
                // Log lỗi lại (có thể dùng một công cụ logging như NLog, Serilog, hoặc log vào database)
                Console.WriteLine($"Error occurred: {ex.Message}");

                // Trả về danh sách rỗng trong trường hợp có lỗi (hoặc ném ngoại lệ tuỳ theo yêu cầu)
                return new List<Drink>();
            }
        }
    }
}
