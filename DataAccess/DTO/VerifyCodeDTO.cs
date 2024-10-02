using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.DTO
{
    public class VerifyCodeDTO
    {
        public string Email { get; set; } = null!;
        public string Code { get; set; } = null!;
    }
}
