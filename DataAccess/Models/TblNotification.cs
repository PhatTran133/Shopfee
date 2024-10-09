using System;
using System.Collections.Generic;

namespace DataAccess.Models
{
    public partial class TblNotification
    {
        public int Id { get; set; }
        public int? UserId { get; set; }
        public string? Content { get; set; }
        public DateTime? CreatedDate { get; set; }
        public DateTime? UpdatedDate { get; set; }
        public DateTime? DeletedDate { get; set; }

        public virtual TblUser? User { get; set; }
    }
}
