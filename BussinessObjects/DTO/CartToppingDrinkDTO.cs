using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class CartToppingDrinkDTO
    {
        public int Id { get; set; }
        public int CartId { get; set; }
        public int ToppingDrinkId { get; set; }
        public DrinkDTO Drink { get; set; } = null!;
        public ToppingDTO Topping { get; set; } = null!;
        public decimal? Quantity {  get; set; }
    }
}
