using BussinessObjects.Models;
using Repositories.Interface;

namespace Repositories
{
	public class TblNotificationRepository : INotificationRepository
	{
		private readonly CoffeeShopContext _context;
		public TblNotificationRepository(CoffeeShopContext context)
		{
			_context = context;
		}
		public async Task CreateNotification(TblNotification model)
		{
			_context.Add(model);
			await _context.SaveChangesAsync();
		}
	}
}
