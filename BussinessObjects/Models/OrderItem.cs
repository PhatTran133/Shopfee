using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.Models
{
    public class OrderItem
    {
        public int Id { get; set; }
        public int OrderId { get; set; }
        public int DrinkId { get; set; }
        public string? Variant { get; set; }
        public string? Size { get; set; }
        public string? Sugar { get; set; }
        public string? Iced { get; set; }
        public string? Note { get; set; }
        public int Quantity { get; set; }
        public decimal TotalPrice { get; set; }

        public virtual TblOrder Order { get; set; } = null!;
        public virtual Drink? Drink { get; set; }
        public ICollection<OrderItemTopping> OrderItemToppings { get; set; } = null!;
    }
}
