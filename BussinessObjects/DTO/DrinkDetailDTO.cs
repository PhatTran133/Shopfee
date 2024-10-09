using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class DrinkDetailDTO
    {
        public int Id { get; set; }
        public string? Name { get; set; }
        public string? Description { get; set; }
        public decimal? Price { get; set; }
        public string CategoryName { get; set; }
        public string? Size { get; set; }
        public string? Image { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public List<string> ToppingNames { get; set; } // Danh sách các tên topping
    }
}
