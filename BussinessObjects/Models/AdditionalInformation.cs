using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.Models
{
    public partial class AdditionalInformation
    {
        public int Id { get; set; }
        public int UserId { get; set; }
        public string Name { get; set; }
        public string Phone { get; set; }
        public string Address { get; set; }

        // Khóa ngoại liên kết với bảng TblUser
        public virtual TblUser User { get; set; }
    }
}
