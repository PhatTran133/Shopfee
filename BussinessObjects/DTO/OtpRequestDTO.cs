using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class OtpRequestDTO
    {
        public string Email { get; set; }
        public string Code { get; set; }
        public DateTime ExpiryTime { get; set; }
    }
}
