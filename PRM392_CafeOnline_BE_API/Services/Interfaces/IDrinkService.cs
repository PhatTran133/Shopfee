using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IDrinkService
    {
        Task<List<DrinkDTO>> GetDrinksByCategoryName(string categoryName);
        Task<DrinkDetailDTO> GetDrinkDetailAsync(int drinkId);


    }
}
