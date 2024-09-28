using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class Category
    {
        public Category()
        {
            Drinks = new HashSet<Drink>();
        }

        public int Id { get; set; }
        public string? Name { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }

        public virtual ICollection<Drink> Drinks { get; set; }
    }
}
