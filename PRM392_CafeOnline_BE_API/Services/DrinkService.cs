using BussinessObjects.DTO;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class DrinkService:IDrinkService
    {
        private readonly IDrinkRepository _drinkRepository;

        public DrinkService(IDrinkRepository drinkRepository)
        {
            _drinkRepository = drinkRepository;
        }

        public async Task<List<DrinkDTO>> GetDrinksByCategoryName(string categoryName)
        {
            return await _drinkRepository.GetDrinksByCategoryAsync(categoryName);
        }

        public async Task<DrinkDetailDTO> GetDrinkDetailAsync(int drinkId)
        {
            return await _drinkRepository.GetDrinkDetailAsync(drinkId);
        }


    }
}
