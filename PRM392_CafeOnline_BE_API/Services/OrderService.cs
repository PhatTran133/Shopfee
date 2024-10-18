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
        private readonly IOrderToppingDrinkRepository _orderToppingDrinkRepository;
        private readonly ICartRepository _cartRepository;
        private readonly ICartToppingDrinkRepository _cartToppingDrinkRepository;
        private readonly IDrinkToppingRepository _drinkToppingRepository;
        private readonly IMapper _mapper;
        public OrderService(IOrderRepository orderRepository, IOrderToppingDrinkRepository orderToppingDrinkRepository,
            ICartRepository cartRepository, ICartToppingDrinkRepository cartToppingDrinkRepository,
            IDrinkToppingRepository drinkToppingRepository, IMapper mapper)
        {
            _orderRepository = orderRepository;
            _orderToppingDrinkRepository = orderToppingDrinkRepository;
            _cartRepository = cartRepository;
            _cartToppingDrinkRepository = cartToppingDrinkRepository;
            _drinkToppingRepository = drinkToppingRepository;
            _mapper = mapper;

        }
        public async Task<OrderDTO> CreateOrder(CreateOrderItemRequestDTO createOrderItemRequestDTO)
        {
            try
            {
                TblOrder orderEntity;

                if (createOrderItemRequestDTO.CartId != null && createOrderItemRequestDTO.CartId != 0)
                {
                    var cart = await _cartRepository.GetCartByIdAsync((int)createOrderItemRequestDTO.CartId);
                    if (cart == null)
                    {
                        throw new Exception("Invalid cart not found");
                    }

                    // Create the order from the cart
                    orderEntity = new TblOrder
                    {
                        CreatedDate = DateTime.Now,
                        UpdatedDate = DateTime.Now,
                        Total = cart.TotalPrice,
                        UserId = createOrderItemRequestDTO.UserId,
                        StatusOfOder = true
                    };

                    await _orderRepository.CreateOrder(orderEntity);

                    // Map cart's CartToppingDrinks to OrderToppingDrink
                    var orderItems = _mapper.Map<List<OrderToppingDrink>>(cart.CartToppingDrinks);

                    foreach (var orderItem in orderItems)
                    {
                        orderItem.OrderId = orderEntity.Id;
                        
                        orderEntity.OrderToppingDrinks.Add(orderItem);
                    }

                    // Save the order topping drinks
                    await _orderToppingDrinkRepository.CreateOrderToppingDrinks(orderItems);

                    foreach (var orderItem in orderEntity.OrderToppingDrinks)
                    {
                        var toppingDrink = await _drinkToppingRepository.FindByIdAsync((int)orderItem.ToppingDrinkId);

                        orderItem.ToppingDrink = toppingDrink;
                    }

                    await _cartRepository.RemoveCartAsync(cart);
                }
                else
                {
                    // Handle case where cart is not provided
                    orderEntity = new TblOrder
                    {
                        CreatedDate = DateTime.Now,
                        UpdatedDate = DateTime.Now,
                        StatusOfOder = true,
                        UserId = createOrderItemRequestDTO.UserId
                    };

                    await _orderRepository.CreateOrder(orderEntity);

                    // Check if the ToppingDrink exists
                    var toppingDrinkExist = await _drinkToppingRepository.FindByIdAsync(createOrderItemRequestDTO.ToppingDrinkId);
                    if (toppingDrinkExist == null)
                    {
                        throw new Exception("Drink and Topping not found");
                    }

                    // Create the order topping drink
                    var orderToppingDrink = _mapper.Map<OrderToppingDrink>(createOrderItemRequestDTO);

                    orderToppingDrink.OrderId = orderEntity.Id;
                    orderEntity.OrderToppingDrinks.Add(orderToppingDrink);
  
                    var price = orderToppingDrink.Quantity * (toppingDrinkExist.Drink.Price + toppingDrinkExist.Topping.Price);
                    orderEntity.Total = price;

                    await _orderToppingDrinkRepository.CreateOrderToppingDrink(orderToppingDrink);
                    
                    await _orderRepository.UpdateOrder(orderEntity);

                    orderToppingDrink.ToppingDrink = toppingDrinkExist;
                }

                // Map and return the created order as OrderDTO, including OrderToppingDrinkDTOs
                var orderDto = _mapper.Map<OrderDTO>(orderEntity);
                return orderDto;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
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
