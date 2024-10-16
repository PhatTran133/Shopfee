using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class OrderToppingDrinkDTO
    {
        public int Id { get; set; }
        public int? Quantity { get; set; }
        public int? ToppingDrinkId { get; set; }
        public string? Variant { get; set; }
        public string? Size { get; set; }
        public string? Sugar { get; set; }
        public string? Iced { get; set; }
        public string? Note { get; set; }
        public DrinkDTO? Drink { get; set; }
        public ToppingDTO? Topping {  get; set; }
    }
}
