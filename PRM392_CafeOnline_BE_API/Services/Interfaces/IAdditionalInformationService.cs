using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IAdditionalInformationService
    {
        Task AddAddressAsync(int userId, string name, string phone, string address);
        Task DeleteAddressAsync(int userId, int addressId);
        Task<List<AddAddressDto>> GetAllAddressesByUserIdAsync(int userId);
    }
}
