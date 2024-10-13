using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class ToppingDrinkRequestDTO
    {
        public int UserId { get; set; }
        public int CartId { get; set; }
        public int ToppingDrinkId { get; set; }
        public int Quantity { get; set; }
    }
}
