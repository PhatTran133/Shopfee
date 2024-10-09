using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IUserService
    {
        Task<bool> CreateUserForOtp(RegisterRequest requestDTO);
        Task<UserDTO> LoginAsync(LoginRequestDTO requestDTO);
        Task<UserDTO> GetUserByUnverifiedEmailAsync(string email);
        Task VerifyUserAccountAsync(int id);
    }
}
