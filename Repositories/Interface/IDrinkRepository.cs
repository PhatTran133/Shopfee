using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface IDrinkRepository
    {
        Task<List<Drink>> SearchDrinksByIdAsync(int id);
        Task<Drink?> GetDrinkByIdAsync(int id);
        Task<List<Drink>> FilterDrinksAsync(string? name, string? categoryName, decimal? minPrice, decimal? maxPrice, DateTime? startDate, DateTime? endDate, string? size);
    }
}
