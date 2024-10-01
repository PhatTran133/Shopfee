using System;
using System.Collections.Generic;

namespace DataAccess.Models
{
    public partial class DrinkTopping
    {
        public DrinkTopping()
        {
            CartToppingDrinks = new HashSet<CartToppingDrink>();
            OrderToppingDrinks = new HashSet<OrderToppingDrink>();
        }

        public int Id { get; set; }
        public int? DrinkId { get; set; }
        public int? ToppingId { get; set; }

        public virtual Drink? Drink { get; set; }
        public virtual Topping? Topping { get; set; }
        public virtual ICollection<CartToppingDrink> CartToppingDrinks { get; set; }
        public virtual ICollection<OrderToppingDrink> OrderToppingDrinks { get; set; }
    }
}
