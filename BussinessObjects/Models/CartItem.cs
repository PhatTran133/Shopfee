using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class CartItem
    {
        public int CartId { get; set; }
        public int DrinkId { get; set; }
        public int Quantity { get; set; }
        public decimal Price { get; set; }
        public decimal? TotalPrice { get; set; }

        public virtual Cart Cart { get; set; } = null!;
        public virtual Drink Drink { get; set; } = null!;
    }
}
