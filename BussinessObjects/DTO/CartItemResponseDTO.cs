using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class CartItemResponseDTO
    {
        public DrinkDTO Drink { get; set; } = null!;
        public int Quantity { get; set; }
        public decimal? TotalPrice { get; set; }
    }
}
