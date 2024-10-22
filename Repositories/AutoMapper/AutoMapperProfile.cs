using AutoMapper;
using BussinessObjects.DTO;
using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.AutoMapper
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<Otp, OtpResponseDTO>();
            CreateMap<TblUser, UserDTO>();

            CreateMap<Drink, DrinkDTO>().ReverseMap();
            CreateMap<Topping, ToppingDTO>().ReverseMap();

            MapCart();
            MapOrder();
            MapOrderCart();
        }

        private void MapOrder()
        {
            CreateMap<TblOrder, OrderDTO>()
                .ForMember(dest => dest.OrderItemDTOs, opt => opt.MapFrom(src => src.OrderItems))
                .ReverseMap();
            CreateMap<OrderItem, OrderItemDTO>()
                .ForMember(dest => dest.DrinkDTO, opt => opt.MapFrom(src => src.Drink))
                .ForMember(dest => dest.OrderItemToppingDTOs, opt => opt.MapFrom(src => src.OrderItemToppings))
                .ReverseMap();
            CreateMap<OrderItemTopping, OrderItemToppingDTO>()
                .ForMember(dest => dest.Topping, opt => opt.MapFrom(src => src.Topping))
                .ReverseMap();
        }

        private void MapOrderCart()
        {
            CreateMap<CartDTO, OrderDTO>()
                            .ForMember(dest => dest.Total, opt => opt.MapFrom(src => src.TotalPrice))
                            .ForMember(dest => dest.OrderItemDTOs, opt => opt.MapFrom(src => src.CartItems))
                            .ReverseMap();
            CreateMap<CartItemDTO, OrderItemDTO>()
                .ForMember(dest => dest.OrderItemToppingDTOs, opt => opt.MapFrom(src => src.CartItemToppingDTOs))
                .ReverseMap();
            CreateMap<CartItemToppingDTO, OrderItemToppingDTO>()
                .ForMember(dest => dest.Topping, opt => opt.MapFrom(src => src.Topping))
                .ReverseMap();
        }

        private void MapCart()
        {
            CreateMap<Cart, CartDTO>()
                .ForMember(dest => dest.CartItems, opt => opt.MapFrom(src => src.CartItems))
                .ReverseMap();
            CreateMap<AddToCartRequestDTO, CartItemDTO>().ReverseMap();
            CreateMap<CartItem, CartItemDTO>()
                .ForMember(dest => dest.CartItemToppingDTOs, opt => opt.MapFrom(src => src.CartItemToppings))
                .ForMember(dest => dest.DrinkDTO, opt => opt.MapFrom(src => src.Drink))
                .ReverseMap();
            CreateMap<CartItemTopping, CartItemToppingDTO>()
                .ForMember(dest => dest.Topping, opt => opt.MapFrom(src => src.Topping))
                .ReverseMap();
            CreateMap<UpdateCartItemRequestDTO, CartItem>().ReverseMap();
        }
    }
}
