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
        public string? Variant { get; set; }
        public string? Size { get; set; }
        public string? Sugar { get; set; }
        public string? Iced { get; set; }
        public string? Note { get; set; }
        public int Total {  get; set; }
    }
}
