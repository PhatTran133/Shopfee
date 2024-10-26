using BussinessObjects.DTO;
using BussinessObjects.Models;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Microsoft.EntityFrameworkCore;


namespace PRM392_CafeOnline_BE_API.Services
{
    public class AdditionalInformationService : IAdditionalInformationService
    {
        private readonly CoffeeShopContext _context;

        public AdditionalInformationService(CoffeeShopContext context)
        {
            _context = context;
        }

        public async Task<int> AddAddressAsync(int userId, string name, string phone, string address)
        {
            var user = await _context.TblUsers.FindAsync(userId);
            if (user != null)
            {
                var newAddress = new AdditionalInformation
                {
                    UserId = userId,
                    Name = name,
                    Phone = phone,
                    Address = address
                };

                _context.AdditionalInformations.Add(newAddress);
                await _context.SaveChangesAsync();

                return newAddress.Id;
            }

            throw new Exception("User not found.");
        }

        public async Task<AddAddressDto> DeleteAddressAsync(int userId, int addressId)
        {
            var address = await _context.AdditionalInformations
                .FirstOrDefaultAsync(a => a.Id == addressId && a.UserId == userId);

            if (address != null)
            {
                _context.AdditionalInformations.Remove(address);
                await _context.SaveChangesAsync();
                return new AddAddressDto
                {
                    UserId = userId,
                    AddressId = addressId
                };
            }
            else
            {
                throw new Exception("Address not found or does not belong to this user.");
            }
        }


        public async Task<List<AddAddressDto>> GetAllAddressesByUserIdAsync(int userId)
        {
            var addresses = await _context.AdditionalInformations
                .Where(a => a.UserId == userId)
                .ToListAsync();

            return addresses.Select(a => new AddAddressDto
            {
                UserId = a.UserId,
                AddressId = a.Id,   // Map đúng trường AddressId từ thực thể
                Name = a.Name,
                Phone = a.Phone,
                Address = a.Address
            }).ToList();
        }

    }
}
