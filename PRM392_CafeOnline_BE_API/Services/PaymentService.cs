using BussinessObjects.Models;
using Microsoft.EntityFrameworkCore;
using PRM392_CafeOnline_BE_API.Services.Interfaces;


namespace PRM392_CafeOnline_BE_API.Services
{
    public class PaymentService : IPaymentService
    {
        private readonly CoffeeShopContext _context;

        public PaymentService(CoffeeShopContext context)
        {
            _context = context;
        }

        public async Task SavePaymentAsync(Payment payment)
        {
            if (payment == null)
            {
                throw new ArgumentNullException(nameof(payment));
            }

            // Lưu thông tin thanh toán vào cơ sở dữ liệu
            await _context.Payments.AddAsync(payment);
            await _context.SaveChangesAsync();
        }

        public async Task<Payment> GetPaymentByOrderIdAsync(Guid orderId)
        {
            return await _context.Payments.FirstOrDefaultAsync(p => p.OrderId.Equals(orderId));
        }

    }



}
