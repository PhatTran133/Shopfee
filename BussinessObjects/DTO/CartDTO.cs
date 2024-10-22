using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class CartDTO
    {
        public int Id { get; set; }
        public int? UserId { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public decimal? TotalPrice { get; set; }
        public List<CartItemDTO>? CartItems { get; set; }
    }
}
