using BussinessObjects.DTO;
using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface ICartToppingDrinkRepository
    {
        Task<CartToppingDrink?> GetCartToppingDrinkByIdAsync(int id);  
        Task<CartToppingDrink?> GetCartToppingDrinkByCartIdAsync(int cartId, int drinkId);
        Task AddCartItemAsync(CartToppingDrink cartToppingDrink);
        Task UpdateCartItemAsync(CartToppingDrink cartToppingDrink);
        Task RemoveCartItemAsync(CartToppingDrink cartToppingDrink);
    }
}
