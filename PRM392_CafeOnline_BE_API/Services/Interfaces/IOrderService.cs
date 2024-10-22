using BussinessObjects.DTO;
using PRM392_CafeOnline_BE_API.Services.Enums;

namespace PRM392_CafeOnline_BE_API.Services.Interfaces
{
    public interface IOrderService
    {
        Task<OrderDTO> CreateOrder(CreateOrderItemRequestDTO createOrderItemRequestDTO);
        Task<IEnumerable<OrderDTO>> GetOrdersByStatus(OrderStatus orderStatus, int userId);
        Task<OrderDTO> GetOrderByIdAsync(int id);
    }
}
