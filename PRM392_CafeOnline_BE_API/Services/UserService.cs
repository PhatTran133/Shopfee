using BussinessObjects.DTO;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class UserService : IUserService
    {
        private readonly IUserRepository _userRepository;
        private readonly IOtpService _otpService;

        public UserService(IUserRepository userRepository, IOtpService otpService)
        {
            _userRepository = userRepository;
            _otpService = otpService;
        }
        public async Task<bool> CreateUserForOtp(RegisterRequest requestDTO)
        {
            return await _userRepository.CreateUserForOtp(requestDTO);
        }

        public async Task<UserDTO> LoginAsync(LoginRequestDTO requestDTO)
        {
            return await _userRepository.LoginAsync(requestDTO);
        }
    }
}
