using BussinessObjects.Models;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class ToppingService : IToppingService
    {
        private readonly IToppingRepository _toppingRepository;

        public ToppingService(IToppingRepository toppingRepository)
        {
            _toppingRepository = toppingRepository;
        }
        public async Task<List<Topping>> GetAllTopping()
        {
            return await _toppingRepository.GetAllTopping();
        }
    }
}
