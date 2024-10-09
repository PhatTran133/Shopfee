using AutoMapper;
using BussinessObjects.DTO;
using DataAccess.Models;
using Microsoft.EntityFrameworkCore;
using Repositories.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories
{
    public class OtpRepository : IOtpRepository
    {
        private readonly CoffeeShopContext _context;
        private readonly IMapper _mapper;

        public OtpRepository(CoffeeShopContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task SaveOtpAsync(OtpRequestDTO requestDTO)
        {
            var otp = new Otp
            {
                Code = requestDTO.Code,
                Email = requestDTO.Email,
                ExpiryTime = requestDTO.ExpiryTime,
            };
            _context.Otps.Add(otp);
            await _context.SaveChangesAsync();
        }

        public async Task<OtpResponseDTO> GetOtpByEmailAsync(string email)
        {
            var otp = await _context.Otps.FirstOrDefaultAsync(x => x.Email == email);
            return _mapper.Map<OtpResponseDTO>(otp);
        }

        public async Task DeleteOtpAsync(string code)
        {
            var otp = await _context.Otps.FirstOrDefaultAsync(x => x.Code == code);
            _context.Otps.Remove(otp);
            await _context.SaveChangesAsync();
        }
    }
}
