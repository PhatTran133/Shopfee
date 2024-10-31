using BussinessObjects.Models;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
	public class NotificationService : INotificationService
	{
		private readonly INotificationRepository _notifiRepository;
		public NotificationService(INotificationRepository notifiRepository)
		{
			_notifiRepository = notifiRepository;
		}

		public async Task CreateData(TblNotification model)
		{
			await _notifiRepository.CreateNotification(model);
		}
	}
}
