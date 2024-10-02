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

        public async Task<TblUser?> GetByEmail(string email)
        {
            return await _context.TblUsers.FirstOrDefaultAsync(x => x.Email == email);
        }

        public async Task<TblUser?> GetTblUser(int id)
        {
            return await _context.TblUsers.FirstOrDefaultAsync(_context => _context.Id == id);
        }

        public async Task UpdateUser(TblUser user)
        {
            _context.Update(user);
            await _context.SaveChangesAsync();
        }
    }
}
