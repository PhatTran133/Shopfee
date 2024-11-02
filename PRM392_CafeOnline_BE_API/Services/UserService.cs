using BussinessObjects.DTO;
using BussinessObjects.Models;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
	public class UserService : IUserService
	{
		private readonly IUserRepository _userRepository;
		private readonly IOtpService _otpService;
		private readonly CoffeeShopContext _context;

		public UserService(IUserRepository userRepository, IOtpService otpService, CoffeeShopContext context)
		{
			_userRepository = userRepository ?? throw new ArgumentNullException(nameof(userRepository));
			_otpService = otpService ?? throw new ArgumentNullException(nameof(otpService));
			_context = context ?? throw new ArgumentNullException(nameof(context));
		}
		public async Task<bool> CreateUserForOtp(RegisterRequest requestDTO)
		{
			return await _userRepository.CreateUserForOtp(requestDTO);
		}

		public async Task<UserDTO> LoginAsync(LoginRequestDTO requestDTO)
		{
			return await _userRepository.LoginAsync(requestDTO);
		}

		public async Task<UserDTO> GetUserByUnverifiedEmailAsync(string email)
		{
			return await _userRepository.GetUserByUnverifiedEmailAsync(email);

		}

		public async Task VerifyUserAccountAsync(int id)
		{
			await _userRepository.VerifyUserAccountAsync(id);
		}

		public async Task<bool> ChangePassword(ChangePasswordDto dto)
		{
			var user = await _context.Set<TblUser>().FindAsync(dto.UserId);
			if (user == null)
			{
				throw new Exception("User not found.");
			}

			if (user.Password != dto.OldPassword)
			{
				throw new Exception("Old password is incorrect.");
			}
			if (dto.NewPassword != dto.ConfirmPassword)
			{
				throw new Exception("New password and confirmation password do not match.");
			}

			user.Password = dto.NewPassword;
			user.UpdatedDate = DateTime.UtcNow;

			await _context.SaveChangesAsync();
			return true;
		}

		public async Task<TblUser?> GetDataById(int id)
		{
			return await _userRepository.GetTblUser(id);
		}
	}

}
