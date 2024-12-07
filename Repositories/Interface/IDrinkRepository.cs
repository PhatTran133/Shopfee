using BussinessObjects.DTO;
using BussinessObjects.Models;
using Microsoft.EntityFrameworkCore;
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
        Task<List<Drink>> FilterDrinksAsync(string? name, string? categoryName, decimal? minPrice, decimal? maxPrice, DateTime? startDate, DateTime? endDate, string? size, bool? descprice, bool? ascName);
        Task<List<DrinkDTO>> GetDrinksByCategoryAsync(string categoryName);

        Task<DrinkDetailDTO> GetDrinkDetailAsync(int drinkId);

    }

}
