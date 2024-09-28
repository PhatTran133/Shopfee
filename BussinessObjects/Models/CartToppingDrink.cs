using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class CartToppingDrink
    {
        public int Id { get; set; }
        public int? CartId { get; set; }
        public int? ToppingDrinkId { get; set; }
        public int? Quantity { get; set; }

        public virtual Cart? Cart { get; set; }
        public virtual DrinkTopping? ToppingDrink { get; set; }
    }
}
