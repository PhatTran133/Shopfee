using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class CartItemTopping
    {
        public int Id { get; set; }
        public int CartItemId { get; set; }
        public int ToppingId {  get; set; }
        public int Price {  get; set; }
        public virtual CartItem CartItem { get; set; } = null!;
        public virtual Topping Topping { get; set; } = null!;
    }
}
