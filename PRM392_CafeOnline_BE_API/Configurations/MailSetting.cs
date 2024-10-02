namespace PRM392_CafeOnline_BE_API.Configurations
{
    public class MailSetting
    {
        public string Username { get; set; } = null!;
        public string Host { get; set; } = null!;
        public int Port { get; set; }
        public string Password { get; set; } = null!;
        public bool EnableSSL { get; set; }
    }
}
