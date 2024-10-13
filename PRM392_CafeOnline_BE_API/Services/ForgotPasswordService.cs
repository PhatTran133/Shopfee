using Microsoft.EntityFrameworkCore;
using PRM392_CafeOnline_BE_API.Configurations;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;
using System.Security.Cryptography;
using System.Text;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class ForgotPasswordService : IForgotPasswordService
    {
        private readonly IUserRepository _userRepository;
        private readonly IEmailService _emailService;
        private readonly InMemoryDbContext _dbContext;

        public ForgotPasswordService(IUserRepository userRepository, IEmailService emailService, InMemoryDbContext dbContext)
        {
            _emailService = emailService;
            _userRepository = userRepository;
            _dbContext = dbContext;
        }

        public async Task<string> GenerateResetCodeAsync(string email)
        {
            var user = await _userRepository.GetByEmail(email) ?? throw new Exception("User not found");

            var code = new Random().Next(100000, 999999).ToString();

            var expiryTime = DateTime.UtcNow.AddMinutes(5);

            // Save the reset code to the in-memory database
            var resetCode = new PasswordResetCode
            {
                Email = email,
                Code = code,
                ExpiryTime = expiryTime
            };

            _dbContext.PasswordResetCodes.Add(resetCode);
            await _dbContext.SaveChangesAsync();

            await _emailService.SendMail(new EmailMetadata
            {
                Subject = "Password Reset Code",
                Body = $"Your password reset code is {code}. It will expire at {expiryTime}",
                ToAddress = user.Email
            });

            return code;
        }

        public async Task ResetPasswordAsync(string email, string code, string newPassword)
        {
            if (!await VerifyResetCodeAsync(email, code))
            {
                throw new Exception("Invalid or expired code.");
            }

            var user = await _userRepository.GetByEmail(email);
            if (user == null)
            {
                throw new Exception("User not found.");
            }

            user.Password = newPassword;
            await _userRepository.UpdateUser(user);

            // Remove the reset code from the in-memory database
            var resetCode = await _dbContext.PasswordResetCodes.FirstOrDefaultAsync(c => c.Email == email);
            if (resetCode != null)
            {
                _dbContext.PasswordResetCodes.Remove(resetCode);
                await _dbContext.SaveChangesAsync();
            }
        }

        public async Task<bool> VerifyResetCodeAsync(string email, string code)
        {
            var resetCode = await _dbContext.PasswordResetCodes
                .FirstOrDefaultAsync(c => c.Email == email && c.Code == code);

            if (resetCode != null && DateTime.UtcNow <= resetCode.ExpiryTime)
            {
                return true;
            }

            return false;
        }

        private string GenerateUniqueCode(string email, string username)
        {
            using (var sha256 = SHA256.Create())
            {
                var bytes = Encoding.UTF8.GetBytes($"{email}{username}{DateTime.UtcNow}");
                var hash = sha256.ComputeHash(bytes);
                return Convert.ToBase64String(hash).Substring(0, 6); // Take first 6 characters as the code
            }
        }
    }
}
