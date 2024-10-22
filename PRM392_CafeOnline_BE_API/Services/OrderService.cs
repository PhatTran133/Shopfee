using AutoMapper;
using BussinessObjects.DTO;
using BussinessObjects.Models;
using PRM392_CafeOnline_BE_API.Services.Enums;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Services
{
    public class OrderService : IOrderService
    {
        private readonly IOrderRepository _orderRepository;
        private readonly IOrderItemRepository _orderItemRepository;
        private readonly IOrderItemToppingRepository _orderItemToppingRepository;
        private readonly ICartRepository _cartRepository;

        private readonly IMapper _mapper;
        public OrderService(IOrderRepository orderRepository,
            ICartRepository cartRepository, IMapper mapper,
            IOrderItemToppingRepository orderItemToppingRepository, IOrderItemRepository orderItemRepository)
        {
            _orderRepository = orderRepository;
            _cartRepository = cartRepository;
            _mapper = mapper;
            _orderItemRepository = orderItemRepository;
            _orderItemToppingRepository = orderItemToppingRepository;
        }
        public async Task<OrderDTO> CreateOrder(CreateOrderItemRequestDTO createOrderItemRequestDTO)
        {
            try
            {
                var existingCart = await _cartRepository.GetCartByUserIdAsync(createOrderItemRequestDTO.UserId);

                var existingCartById = await _cartRepository.GetCartByIdAsync(createOrderItemRequestDTO.CartId);

                if(existingCart == null || existingCart != existingCartById)
                {
                    throw new Exception("Invalid cart");
                }

                var existingCartDTO = _mapper.Map<CartDTO>(existingCart);
                var newOrder = _mapper.Map<OrderDTO>(existingCartDTO);
                newOrder.StatusOfOder = true;
                await _orderRepository.CreateOrder(_mapper.Map<TblOrder>(newOrder));
                OrderItem orderItem;
                OrderItemTopping orderItemTopping;
                foreach(var item in newOrder.OrderItemDTOs)
                {
                    orderItem = _mapper.Map<OrderItem>(item);
                    orderItem.DrinkId = item.DrinkDTO.Id;
                    orderItem.OrderId = newOrder.Id;
                    await _orderItemRepository.AddOrderItemAsync(orderItem);

                    foreach(var itemTopping in item.OrderItemToppingDTOs)
                    {
                        orderItemTopping = new()
                        {
                            OrderItemId = item.Id,
                            ToppingId = itemTopping.Topping.Id
                        };

                        await _orderItemToppingRepository.AddOrderItemToppingAsync(orderItemTopping);
                    }
                }
                await _cartRepository.RemoveCartAsync(existingCart);
                return newOrder;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public async Task<OrderDTO> GetOrderByIdAsync(int id)
        {
            try
            {
                var order = await _orderRepository.GetOrderByIdAsync(id);
                return _mapper.Map<OrderDTO>(order);
            }catch(Exception ex)
            {
                throw;
            }
        }

        public async Task<IEnumerable<OrderDTO>> GetOrdersByStatus(OrderStatus orderStatus, int userId)
        {
            try
            {
                bool status = false;
                bool paymentCreated = false;
                switch (orderStatus)
                {
                    case OrderStatus.SUCCESS:
                        status = true;
                        paymentCreated = true;
                        break;
                    case OrderStatus.PENDING:
                        status = true;
                        paymentCreated = false;
                        break;
                    case OrderStatus.FAILED:
                        status = false;
                        paymentCreated = false;
                        break;
                    default:
                        status = false;
                        break;
                }

                var orders = await _orderRepository.GetOrdersFilterByStatus(status, paymentCreated, userId);

                var ordersDto = _mapper.Map<IEnumerable<OrderDTO>>(orders);
                return ordersDto;
            }catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
    }
}
