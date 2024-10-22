using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class OrderDTO
    {
        public int Id { get; set; }
        public int? UserId { get; set; }
        public decimal? Total { get; set; }
        public bool? StatusOfOder { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public DateTime? DeletedDate { get; set; }
        public List<OrderItemDTO> OrderItemDTOs { get; set; } = null!;
    }
}
