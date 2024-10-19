using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class DeleteExpiredUsersService : IHostedService, IDisposable
    {
        private readonly ILogger<DeleteExpiredUsersService> _logger;
        private readonly IServiceScopeFactory _scopeFactory;
        private Timer _timer;

        public DeleteExpiredUsersService(ILogger<DeleteExpiredUsersService> logger, IServiceScopeFactory scopeFactory)
        {
            _logger = logger;
            _scopeFactory = scopeFactory;
        }

        public Task StartAsync(CancellationToken cancellationToken)
        {
            _logger.LogInformation("DeleteExpiredUsersService is starting.");
            _timer = new Timer(DeleteExpiredUsers, null, TimeSpan.Zero, TimeSpan.FromMinutes(1));
            return Task.CompletedTask;
        }

        private void DeleteExpiredUsers(object state)
        {
            using (var scope = _scopeFactory.CreateScope())
            {
                var userRepository = scope.ServiceProvider.GetRequiredService<IUserRepository>();

                var deleteTask = userRepository.DeleteExpiredUsersAsync();
                deleteTask.Wait();
                _logger.LogInformation("DeleteExpiredUsersService has done successfully.");
            }
        }

        public Task StopAsync(CancellationToken cancellationToken)
        {
            _logger.LogInformation("DeleteExpiredUsersService is stopping.");

            _timer?.Change(Timeout.Infinite, 0);
            return Task.CompletedTask;
        }

        public void Dispose()
        {
            _timer?.Dispose();
        }
    }

}
