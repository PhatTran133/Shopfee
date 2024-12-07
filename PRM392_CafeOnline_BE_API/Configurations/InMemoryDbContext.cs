using Microsoft.EntityFrameworkCore;

namespace PRM392_CafeOnline_BE_API.Configurations
{
    public class InMemoryDbContext : DbContext
    {
        public DbSet<PasswordResetCode> PasswordResetCodes { get; set; }

        public InMemoryDbContext(DbContextOptions<InMemoryDbContext> options)
            : base(options)
        {
        }
    }
}
