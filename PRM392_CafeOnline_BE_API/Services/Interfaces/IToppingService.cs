using BussinessObjects.Models;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IToppingService
    {
        Task<List<Topping>> GetAllTopping();
    }
}
