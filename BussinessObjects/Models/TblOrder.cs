using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class TblOrder
    {
        public int Id { get; set; }
        public int? UserId { get; set; }
        public decimal? Total { get; set; }
        public bool? StatusOfOder { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public DateTime? DeletedDate { get; set; }

        public virtual TblUser User { get; set; } = null!;
        public virtual ICollection<OrderItem> OrderItems { get; set; } = null!;
        public virtual ICollection<Payment>? Payments { get; set; }
    }
}
