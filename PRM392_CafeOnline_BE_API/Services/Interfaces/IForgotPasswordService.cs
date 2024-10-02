namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IForgotPasswordService
    {
        Task<string> GenerateResetCodeAsync(string email);
        Task<bool> VerifyResetCodeAsync(string email, string code);
        Task ResetPasswordAsync(string email, string code, string newPassword);
    }
}
