using BussinessObjects.Models;

namespace Repositories.Interface
{
	public interface INotificationRepository
	{
		Task CreateNotification(TblNotification model);
	}
}
