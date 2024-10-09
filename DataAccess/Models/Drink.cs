using System;
using System.Collections.Generic;

namespace DataAccess.Models
{
    public partial class Drink
    {
        public Drink()
        {
            CartItems = new HashSet<CartItem>();
            DrinkToppings = new HashSet<DrinkTopping>();
        }

        public int Id { get; set; }
        public int? CategoryId { get; set; }
        public string? Name { get; set; }
        public string? Description { get; set; }
        public decimal? Price { get; set; }
        public string? Size { get; set; }
        public string? Image { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public bool? IsDeleted { get; set; }

        public virtual Category? Category { get; set; }
        public virtual ICollection<CartItem> CartItems { get; set; }
        public virtual ICollection<DrinkTopping> DrinkToppings { get; set; }
    }
}
