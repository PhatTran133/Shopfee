using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.Models
{
    public class CartItem
    {
        public int Id {  get; set; }
        public int CartId {  get; set; }
        public int DrinkId {  get; set; }
        public string? Variant { get; set; }
        public string? Size { get; set; }
        public string? Sugar { get; set; }
        public string? Iced { get; set; }
        public string? Note { get; set; }
        public int Quantity { get; set; }
        public decimal TotalPrice {  get; set; }
        public Cart Cart { get; set; } = null!;
        public Drink Drink { get; set; } = null!;
        public ICollection<CartItemTopping> CartItemToppings { get; set; } = null!;
    }
}
