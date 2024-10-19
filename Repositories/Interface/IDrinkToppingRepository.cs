using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface IDrinkToppingRepository
    {
        Task<DrinkTopping?> FindByIdAsync(int id);
        Task AddDrinkTopping(DrinkTopping drinkTopping);
        Task<DrinkTopping?> FindByDrinkIdAndToppingId(int drinkId, int toppingId);
    }
}
