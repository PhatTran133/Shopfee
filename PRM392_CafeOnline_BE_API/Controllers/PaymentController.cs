using BussinessObjects.DTO;
using BussinessObjects.Models;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;

namespace PRM392_CafeOnline_BE_API.Controllers
{
	[ApiController]
	[Route("api/[controller]")]
	public class PaymentController : ControllerBase
	{
		private readonly IPaymentService _paymentService;
		private readonly IVnPayService _vnpayService; // Giả sử có một service để xử lý VNPay
		private readonly IOrderService _orderService;
		private readonly IUserService _userService;
		private readonly INotificationService _notificationService;


		public PaymentController(IPaymentService paymentService,
				IVnPayService vnpayService,
				IOrderService orderService,
				IUserService userService,
				INotificationService notifiService)
		{
			_paymentService = paymentService;
			_vnpayService = vnpayService;
			_orderService = orderService;
			_userService = userService;
			_notificationService = notifiService;
		}

		// POST: api/VNPay/CreatePaymentUrl
		[HttpPost("CreatePaymentUrl")]
        public async Task<IActionResult> CreatePaymentUrlAsync([FromBody] PaymentInformationModel model)
        {
            if (model == null || model.Amount <= 0)
            {
                return BadRequest(new JsonResponse<object>(null, 400, "Invalid payment information"));
            }
            if (await _orderService.GetOrderByIdAsync(model.OrderId) == null)
            {
                return BadRequest(new JsonResponse<object>(null, 400, "Order not found"));
            }
            if (await _userService.GetDataById(model.UserId) == null)
            {
                return BadRequest(new JsonResponse<object>(null, 400, "User not found"));
            }

            var paymentUrl = _vnpayService.CreatePaymentUrl(model, HttpContext);

            if (!string.IsNullOrEmpty(paymentUrl))
            {
                return Ok(new JsonResponse<object>(new { PaymentUrl = paymentUrl }, 200, "Payment URL created successfully"));
            }

            return StatusCode(500, new JsonResponse<object>(null, 500, "Failed to create payment URL"));
        }

        // GET: api/VNPay/PaymentCallback
        [HttpGet("PaymentCallback")]
        public async Task<IActionResult> PaymentCallbackAsync()
        {
            var paymentResponse = _vnpayService.PaymentExecute(Request.Query);

            if (paymentResponse == null || paymentResponse.VnPayResponseCode != "00")
            {
                return BadRequest(new JsonResponse<object>(paymentResponse, 400, "Payment failed"));
            }

            string[] infoSplit = paymentResponse.OrderDescription.Split('_');
            Payment pay = new Payment()
            {
                OrderId = int.Parse(infoSplit[1]),
                Type = infoSplit[3],
                Detail = $"{infoSplit[2]}_{infoSplit[4]}_{infoSplit[5]}",
                CreatedDate = DateTime.Now
            };
            await _paymentService.SavePaymentAsync(pay);

            TblNotification notifi = new TblNotification()
            {
                UserId = int.Parse(infoSplit[0]),
                Content = $"{infoSplit[2]}_{infoSplit[4]}_{infoSplit[5]}",
                CreatedDate = DateTime.Now
            };
            await _notificationService.CreateData(notifi);

            return Ok(new JsonResponse<object>(paymentResponse, 200, "Payment successful"));
        }

    }

}
