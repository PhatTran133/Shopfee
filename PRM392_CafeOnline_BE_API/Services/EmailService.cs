using FluentEmail.Core;
using PRM392_CafeOnline_BE_API.Configurations;
using PRM392_CafeOnline_BE_API.Services.Interfaces;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class EmailService : IEmailService
    {
        private readonly IFluentEmail _fluentEmail;
        public EmailService(IFluentEmail fluentEmail)
        {
            _fluentEmail = fluentEmail;
        }
        public async Task SendMail(EmailMetadata emailMetadata)
        {
            await _fluentEmail.To(emailMetadata.ToAddress)
                .Body(emailMetadata.Body)
                .Subject(emailMetadata.Subject)
                .SendAsync();
        }
    }
}
