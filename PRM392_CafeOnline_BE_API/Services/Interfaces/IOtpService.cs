using BussinessObjects.DTO;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IOtpService
    {
        Task<bool> GenerateOtpAndSendEmail(string email);
        Task SaveOtpAsync(OtpRequestDTO requestDTO);
        Task<OtpResponseDTO> GetOtpByEmailAsync(string email);
        Task DeleteOtpAsync(string code);
    }
}
