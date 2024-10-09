using System;
using System.Collections.Generic;

namespace DataAccess.Models
{
    public partial class OrderToppingDrink
    {
        public int Id { get; set; }
        public int? Quantity { get; set; }
        public int? OrderId { get; set; }
        public int? ToppingDrinkId { get; set; }

        public virtual TblOrder? Order { get; set; }
        public virtual DrinkTopping? ToppingDrink { get; set; }
    }
}
