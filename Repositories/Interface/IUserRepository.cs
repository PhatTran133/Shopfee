using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface IUserRepository
    {
        Task<TblUser> GetTblUser(int id);
        Task<TblUser> GetUserByEmailAsync(string email);
        Task AddUserAsync(TblUser user);
        Task UpdateUser(TblUser user);
    }
}
