using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.Models
{
    public class OrderItemTopping
    {
        public int Id { get; set; }
        public int OrderItemId { get; set; }
        public int ToppingId { get; set; }
        public virtual OrderItem OrderItem { get; set; } = null!;
        public virtual Topping Topping { get; set; } = null!;
    }
}
