using System;
using System.Collections.Generic;

namespace BussinessObjects.Models
{
    public partial class Topping
    {
        public Topping()
        {
            DrinkToppings = new HashSet<DrinkTopping>();
        }

        public int Id { get; set; }
        public string? Name { get; set; }
        public decimal? Price { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }

        public virtual ICollection<DrinkTopping> DrinkToppings { get; set; }
    }
}
