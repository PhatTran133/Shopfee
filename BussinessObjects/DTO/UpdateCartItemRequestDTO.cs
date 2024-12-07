using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class UpdateCartItemRequestDTO
    {
        public int Quantity { get; set; }
        public string? Variant { get; set; }
        public string? Size { get; set; }
        public string? Sugar { get; set; }
        public string? Iced { get; set; }
        public string? Note { get; set; }
        public int UnitPrice { get; set; }
        public int TotalPrice {  get; set; }
    }
}
