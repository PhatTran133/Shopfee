using BussinessObjects.Models;
using Microsoft.EntityFrameworkCore;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class TblUserRepository : IUserRepository
    {
        private readonly CoffeeShopContext _context;
        public TblUserRepository(CoffeeShopContext context)
        {
            _context = context;
        }

        public async Task AddUserAsync(TblUser user)
        {
            // Thêm người dùng mới vào DbSet
            await _context.TblUsers.AddAsync(user);

            // Lưu các thay đổi vào cơ sở dữ liệu
            await _context.SaveChangesAsync();
        }


        public async Task<TblUser> GetTblUser(int id)
        {
            return await _context.TblUsers.FirstOrDefaultAsync(_context => _context.Id == id) ?? throw new Exception("Not found user");
        }

        public async Task<TblUser> GetUserByEmailAsync(string email)
        {
            return await _context.TblUsers.FirstOrDefaultAsync(u => u.Email == email);
        }
    }
}
