using System;
using System.Collections.Generic;

namespace DataAccess.Models
{
    public partial class Payment
    {
        public int Id { get; set; }
        public int? OrderId { get; set; }
        public string? Type { get; set; }
        public string? Detail { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public DateTime? DeletedDate { get; set; }

        public virtual TblOrder? Order { get; set; }
    }
}
