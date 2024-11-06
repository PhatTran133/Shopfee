using BussinessObjects.DTO;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Enums;
using PRM392_CafeOnline_BE_API.Services.Interfaces;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [ApiController]
    [Route("api/orders")]
    public class OrderController : ControllerBase
    {
        private readonly IOrderService _orderService;
        public OrderController(IOrderService orderService)
        {
            _orderService = orderService;
        }

        [HttpPost]
        public async Task<IActionResult> CreateOrder(CreateOrderItemRequestDTO createOrderItemRequestDTO)
        {
            try
            {
                var response = await _orderService.CreateOrder(createOrderItemRequestDTO);
                return Ok(new JsonResponse<int>(response.Id, 200, "Created successfully"));
            } catch (Exception ex)
            {
                if (ex.Message != null)
                {
                    return BadRequest(new JsonResponse<string>("Error", 400, ex.Message));
                }
                return StatusCode(500, new JsonResponse<string>("Internal Error", 500, ex.Message));
            }
        }
        [HttpGet("status/{userId}")]
        public async Task<IActionResult> GetOrdersByStatus([FromQuery]OrderStatus orderStatus, int userId)
        {
            try
            {
                var orders = await _orderService.GetOrdersByStatus(orderStatus, userId);
                return Ok(new JsonResponse<IEnumerable<OrderDTO>>(orders, 200, "Get orders successfully"));
            }catch(Exception ex)
            {
                if (ex.Message != null)
                {
                    return BadRequest(new JsonResponse<string>("Error", 400, ex.Message));
                }
                return StatusCode(500, new JsonResponse<string>("Internal Error", 500, ex.Message));
            }
        }
        [HttpGet("{orderId}")]
        public async Task<IActionResult> GetOrderById(int orderId)
        {
            try
            {
             
                var order = await _orderService.GetOrderByIdAsync(orderId);
                return Ok(new JsonResponse<OrderDTO>(order, 200, "Get Order Successfully"));
            }
            catch (Exception ex)
            {
                if (ex.Message != null)
                {
                    return BadRequest(new JsonResponse<string>("Error", 400, ex.Message));
                }
                return StatusCode(500, new JsonResponse<string>("Internal Error", 500, ex.Message));
            }
        }
    }
}
