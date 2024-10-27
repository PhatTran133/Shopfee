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
        private readonly IPaymentService _paymentService;

        private readonly IMapper _mapper;
        public OrderService(IOrderRepository orderRepository,
            ICartRepository cartRepository, IMapper mapper,
            IOrderItemToppingRepository orderItemToppingRepository, IOrderItemRepository orderItemRepository, IPaymentService paymentService)
        {
            _orderRepository = orderRepository;
            _cartRepository = cartRepository;
            _mapper = mapper;
            _orderItemRepository = orderItemRepository;
            _orderItemToppingRepository = orderItemToppingRepository;
            _paymentService = paymentService;
        }
        public async Task<OrderDTO> CreateOrder(CreateOrderItemRequestDTO createOrderItemRequestDTO)
        {
            try
            {
                var existingCart = await _cartRepository.GetCartByUserIdAsync(createOrderItemRequestDTO.UserId);

                var existingCartById = await _cartRepository.GetCartByIdAsync(createOrderItemRequestDTO.CartId);

                if (existingCart == null || existingCart != existingCartById)
                {
                    throw new Exception("Invalid cart");
                }

                var newOrder = new TblOrder
                {
                    CreatedDate = DateTime.Now,
                    StatusOfOder = true,
                    UserId = createOrderItemRequestDTO.UserId
                };
                await _orderRepository.CreateOrder(newOrder);
                foreach (var item in existingCart.CartItems)
                {
                    var orderItem = _mapper.Map<OrderItem>(item);
                    orderItem.OrderId = newOrder.Id;
                    await _orderItemRepository.AddOrderItemAsync(orderItem);

                    if (item.CartItemToppings != null)
                    {
                        foreach (var cartItemTopping in item.CartItemToppings)
                        {
                            var orderItemTopping = new OrderItemTopping
                            {
                                OrderItemId = orderItem.Id,
                                ToppingId = cartItemTopping.ToppingId
                            };

                            await _orderItemToppingRepository.AddOrderItemToppingAsync(orderItemTopping);
                        }
                    }
                }
                await _cartRepository.RemoveCartAsync(existingCart);
                newOrder.Total = existingCart.TotalPrice;
                await _orderRepository.UpdateOrder(newOrder);

                var payment = new Payment()
                {
                    OrderId = newOrder.Id,
                    CreatedDate = DateTime.Now,
                    Detail = createOrderItemRequestDTO.PaymentRequest.Detail,
                    Type = createOrderItemRequestDTO.PaymentRequest.Type
                };
                await _paymentService.SavePaymentAsync(payment);
                var orderCreated = await _orderRepository.GetOrderByIdAsync(newOrder.Id);
                return _mapper.Map<OrderDTO>(orderCreated);
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
            }
            catch (Exception ex)
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
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
    }
}
