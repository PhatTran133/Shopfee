using BussinessObjects.DTO;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class OtpService:IOtpService
    {
        private readonly IEmailService _emailService;
        private readonly IOtpRepository _otpRepository;

        public OtpService(IEmailService emailService, IOtpRepository otpRepository)
        {
            _emailService = emailService;
            _otpRepository = otpRepository;
        }
        public async Task<bool> GenerateOtpAndSendEmail(string email)
        {
            // Tạo mã OTP và lưu vào database
            var otpCode = new Random().Next(100000, 999999).ToString();
            var otpExpiryTime = DateTime.Now.AddMinutes(5); // Mã OTP hết hạn sau 5 phút
            await _otpRepository.SaveOtpAsync(new OtpRequestDTO
            {
                Email = email,
                Code = otpCode,
                ExpiryTime = otpExpiryTime
            });

            // Gửi mã OTP qua email
            var subject = "Xác nhận tài khoản của bạn";
            var body = $"Mã xác nhận của bạn là {otpCode}. Mã này sẽ hết hạn sau 5 phút.";
            await _emailService.SendEmailAsync(email, subject, body);

            return true;
        }

        public async Task<OtpResponseDTO> GetOtpByEmailAsync(string email)
        {
            return await _otpRepository.GetOtpByEmailAsync(email);
        }

        public async Task SaveOtpAsync(OtpRequestDTO otpEntity)
        {
            await _otpRepository.SaveOtpAsync(otpEntity);
        }

        public async Task DeleteOtpAsync(string code)
        {
            await _otpRepository.DeleteOtpAsync(code);
        }

    }
}
