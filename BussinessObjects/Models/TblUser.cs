using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class TblUser
    {
        public TblUser()
        {
            Carts = new HashSet<Cart>();
            TblNotifications = new HashSet<TblNotification>();
            TblOrders = new HashSet<TblOrder>();
        }

        public int Id { get; set; }
       // public int? RoleId { get; set; }
        public string? Username { get; set; }
        public string? Email { get; set; }
        public string? Password { get; set; }
        public string? Phone { get; set; }
        public string? Address { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }

        public string Otp { get; set; }

        // Thuộc tính mới để lưu thời gian hết hạn của OTP
        public DateTime OtpExpiration { get; set; }

        // Thuộc tính mới để lưu trạng thái xác thực email
        public bool EmailVerified { get; set; } // Cờ để xác định email đã xác thực

        public virtual ICollection<Cart> Carts { get; set; }
        public virtual ICollection<TblNotification> TblNotifications { get; set; }
        public virtual ICollection<TblOrder> TblOrders { get; set; }
    }
}
