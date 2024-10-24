using BussinessObjects.Models;


namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IPaymentService
    {
        Task SavePaymentAsync(Payment payment);
        Task<Payment> GetPaymentByOrderIdAsync(Guid orderId);       
    }

}
