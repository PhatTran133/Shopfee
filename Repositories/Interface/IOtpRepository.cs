using BussinessObjects.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface IOtpRepository
    {
        Task SaveOtpAsync(OtpRequestDTO requestDTO);
        Task<OtpResponseDTO> GetOtpByEmailAsync(string email);
        Task DeleteOtpAsync(string code);
    }
}
