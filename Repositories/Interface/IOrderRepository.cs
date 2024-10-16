using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface IOrderRepository
    {
        Task CreateOrder(TblOrder tblOrder);
        Task UpdateOrder(TblOrder tblOrder);
        Task<IEnumerable<TblOrder>> GetOrdersFilterByStatus(bool status, bool paymentCreated, int userId);
    }
}
