using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class Cart
    {
        public Cart()
        {
            CartItems = new HashSet<CartItem>();
            CartToppingDrinks = new HashSet<CartToppingDrink>();
        }

        public int? UserId { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public DateTime? DeletedDate { get; set; }
        public decimal? TotalPrice { get; set; }
        public int Id { get; set; }

        public virtual TblUser? User { get; set; }
        public virtual ICollection<CartItem> CartItems { get; set; }
        public virtual ICollection<CartToppingDrink> CartToppingDrinks { get; set; }
    }
}
