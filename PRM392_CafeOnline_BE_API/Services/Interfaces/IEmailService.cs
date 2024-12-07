using PRM392_CafeOnline_BE_API.Configurations;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IEmailService
    {
        Task SendMail(EmailMetadata emailMetadata);
        Task SendEmailAsync(string toEmail, string subject, string body);
    }
}
