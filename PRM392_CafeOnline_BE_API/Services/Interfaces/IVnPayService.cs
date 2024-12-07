using BussinessObjects.DTO;


namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IVnPayService
    {
        string CreatePaymentUrl(PaymentInformationModel model, HttpContext context);
        VNPayPaymentResponseModel PaymentExecute(IQueryCollection collections);
        //string CreatePaymentUrl(double amount, string bookingId, string name);
        //bool ValidateSignature(VNPayCallbackModel model);
        //string CreatePaymentUrl(VNPayPaymentRequestModel model, HttpContext context);
        //VNPayPaymentResponseModel PaymentExecute(IQueryCollection collections);
    }
}
