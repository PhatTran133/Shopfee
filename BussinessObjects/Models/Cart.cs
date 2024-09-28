using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class Cart
    {
        public Cart()
        {
            CartToppingDrinks = new HashSet<CartToppingDrink>();
        }

        public int Id { get; set; }
        public int? UserId { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public DateTime? DeletedDate { get; set; }
        public decimal? TotalPrice { get; set; }

        public virtual TblUser? User { get; set; }
        public virtual ICollection<CartToppingDrink> CartToppingDrinks { get; set; }
    }
}
