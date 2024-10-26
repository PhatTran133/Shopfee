using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BussinessObjects.DTO
{
    public class CreateOrderItemRequestDTO
    {
        public int UserId { get; set; }
        public int CartId {  get; set; }
        public PaymentRequestDTO PaymentRequest { get; set; } = null!;
    }
}