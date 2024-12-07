using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IAdditionalInformationService
    {
        Task<int> AddAddressAsync(int userId, string name, string phone, string address);      
        Task<AddAddressDto> DeleteAddressAsync(int userId, int addressId);
        Task<List<AddAddressDto>> GetAllAddressesByUserIdAsync(int userId);
    }
}
