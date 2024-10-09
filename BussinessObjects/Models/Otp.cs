using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class Otp
    {
        public int Id { get; set; }
        public string Email { get; set; } = null!;
        public string Code { get; set; } = null!;
        public DateTime ExpiryTime { get; set; }
    }
}
