using AutoMapper;
using BussinessObjects.DTO;
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
        private readonly IMapper _mapper;

        public TblUserRepository(CoffeeShopContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task AddUserAsync(TblUser user)
        {
            // Thêm người dùng mới vào DbSet
            await _context.TblUsers.AddAsync(user);

            // Lưu các thay đổi vào cơ sở dữ liệu
            await _context.SaveChangesAsync();
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

        public async Task<TblUser> GetUserByEmailAsync(string email)
        {
            return await _context.TblUsers.FirstOrDefaultAsync(u => u.Email == email);
        }

        public async Task<TblUser?> GetByEmail(string email)
        {
            return await _context.TblUsers.FirstOrDefaultAsync(x => x.Email == email);
        }

        public async Task<bool> CreateUserForOtp(RegisterRequest requestDTO)
        {
            var user = await _context.TblUsers.AnyAsync(x => x.Email.Equals(requestDTO.Email) && x.EmailVerified == true);
            if (user) throw new Exception("Email is used");

            var newUser = new TblUser
            {
                Username = requestDTO.Username,
                Email = requestDTO.Email,
                Password = requestDTO.Password,
                Address = requestDTO.Address,
                Phone = requestDTO.Phone,
                CreatedDate = DateTime.Now,
                UpdatedDate = DateTime.Now,
                EmailVerified = false
            };

            _context.TblUsers.Add(newUser);
            if (await _context.SaveChangesAsync() > 0)
                return true;
            else
                return false;
        }

        public async Task<UserDTO> LoginAsync(LoginRequestDTO requestDTO)
        {
            var user = await _context.TblUsers.FirstOrDefaultAsync(x => x.Email.Equals(requestDTO.Email));
            if (user == null || !user.Password.Equals(requestDTO.Password)) throw new Exception("Wrong Email or Password");
            if (user.EmailVerified == false) throw new Exception("Please Verify Your Email.");
            return _mapper.Map<UserDTO>(user);
        }

        public async Task<UserDTO> GetUserByUnverifiedEmailAsync(string email)
        {
            var user = await _context.TblUsers.FirstOrDefaultAsync(x => x.Email == email && x.EmailVerified == false);

            return _mapper.Map<UserDTO>(user);
        }

        public async Task VerifyUserAccountAsync(int id)
        {
            var user = _context.TblUsers.FirstOrDefault(x => x.Id == id);

            user.EmailVerified = true;           
            _context.TblUsers.Update(user);
            await _context.SaveChangesAsync();
        }
    }

}
