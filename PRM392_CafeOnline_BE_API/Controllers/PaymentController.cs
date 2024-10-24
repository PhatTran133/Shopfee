using BussinessObjects.DTO;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PRM392_CafeOnline_BE_API.Services.Interfaces;


namespace PRM392_CafeOnline_BE_API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class PaymentController : ControllerBase
    {
        private readonly IPaymentService _paymentService;
        private readonly IVnPayService _vnpayService; // Giả sử có một service để xử lý VNPay

        public PaymentController(IPaymentService paymentService, IVnPayService vnpayService)
        {
            _paymentService = paymentService;
            _vnpayService = vnpayService;
        }

        // POST: api/VNPay/CreatePaymentUrl
        [HttpPost("CreatePaymentUrl")]
        public IActionResult CreatePaymentUrl([FromBody] PaymentInformationModel model)
        {
            if (model == null || model.Amount <= 0)
            {
                return BadRequest(new { message = "Invalid payment information" });
            }

            var paymentUrl = _vnpayService.CreatePaymentUrl(model, HttpContext);

            if (!string.IsNullOrEmpty(paymentUrl))
            {
                return Ok(new { PaymentUrl = paymentUrl });
            }

            return StatusCode(500, new { message = "Failed to create payment URL" });
        }

        // GET: api/VNPay/PaymentCallback
        [HttpGet("PaymentCallback")]
        public IActionResult PaymentCallback()
        {
            var queryParams = Request.Query;

            if (queryParams.Count == 0)
            {
                return BadRequest(new { message = "No query parameters found" });
            }

            var paymentResponse = _vnpayService.PaymentExecute(queryParams);

            if (paymentResponse != null && paymentResponse.VnPayResponseCode == "200")
            {
                // Xử lý thành công thanh toán
                return Ok(new { message = "Payment successful", paymentResponse });
            }

            // Xử lý thanh toán thất bại
            return BadRequest(new { message = "Payment failed", paymentResponse });
        }
    }
      
}
