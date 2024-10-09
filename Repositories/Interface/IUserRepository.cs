using BussinessObjects.DTO;
using DataAccess.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface IUserRepository
    {
        Task<TblUser?> GetTblUser(int id);
        Task<TblUser> GetUserByEmailAsync(string email);
        Task AddUserAsync(TblUser user);
        Task UpdateUser(TblUser user);
        Task<TblUser?> GetByEmail(string email);
        Task<bool> CreateUserForOtp(RegisterRequest requestDTO);
        Task<UserDTO> LoginAsync(LoginRequestDTO requestDTO);
        Task<UserDTO> GetUserByUnverifiedEmailAsync(string email);
        Task VerifyUserAccountAsync(int id);
    }
}
