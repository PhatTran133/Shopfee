namespace PRM392_CafeOnline_BE_API.Configurations
{
    public static class MailConfig
    {
        public static void AddFluentEmailExtension(this IServiceCollection services, IConfiguration configuration)
        {
            var mailSettings = configuration.GetSection("SmtpSettings").Get<MailSetting>();

            services.AddFluentEmail(mailSettings.Username)
                .AddSmtpSender(mailSettings.Host, mailSettings.Port, mailSettings.Username, mailSettings.Password);
        }
    }
}
