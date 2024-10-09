using System;
using System.Collections.Generic;

namespace DataAccess.Models
{
    public partial class TblOrder
    {
        public TblOrder()
        {
            OrderToppingDrinks = new HashSet<OrderToppingDrink>();
            Payments = new HashSet<Payment>();
        }

        public int Id { get; set; }
        public int? UserId { get; set; }
        public decimal? Total { get; set; }
        public bool? StatusOfOder { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public DateTime? DeletedDate { get; set; }

        public virtual TblUser? User { get; set; }
        public virtual ICollection<OrderToppingDrink> OrderToppingDrinks { get; set; }
        public virtual ICollection<Payment> Payments { get; set; }
    }
}
