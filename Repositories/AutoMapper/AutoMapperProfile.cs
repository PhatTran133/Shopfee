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
            CreateMap<Cart, CartDTO>()
                .ForMember(dest => dest.CartItems, opt => opt.MapFrom(src => src.CartToppingDrinks))
                .ReverseMap();
            CreateMap<AddToCartRequestDTO, CartToppingDrink>()
                .ForMember(dest => dest.ToppingDrink, opt => opt.Ignore());
            CreateMap<CartToppingDrink, CartToppingDrinkDTO>()
                .ForMember(dest => dest.Drink, opt => opt.MapFrom(src => src.ToppingDrink.Drink))
                .ForMember(dest => dest.Topping, opt => opt.MapFrom(src => src.ToppingDrink.Topping))
                .ReverseMap();
            CreateMap<UpdateCartItemRequestDTO, CartToppingDrink>().ReverseMap();
            CreateMap<Drink, DrinkDTO>().ReverseMap();
            CreateMap<Topping, ToppingDTO>().ReverseMap();

            CreateMap<TblOrder, OrderDTO>()
                .ForMember(dest => dest.OrderToppingDrinkDTOs, opt => opt.MapFrom(src => src.OrderToppingDrinks))
                .ReverseMap();
            CreateMap<CartToppingDrink, OrderToppingDrink>()
                .ForMember(dest => dest.Id, opt => opt.Ignore())
                .ForMember(dest => dest.ToppingDrinkId, opt => opt.MapFrom(src => src.ToppingDrinkId)) // Map ToppingDrinkId directly
                .ForMember(dest => dest.ToppingDrink, opt => opt.Ignore()) // Ignore ToppingDrink to avoid tracking issues
                .ReverseMap();
            CreateMap<CreateOrderItemRequestDTO, OrderToppingDrink>().ReverseMap();
            CreateMap<OrderToppingDrink, OrderToppingDrinkDTO>()
                .ForMember(dest => dest.Drink, opt => opt.MapFrom(src => src.ToppingDrink.Drink))
                .ForMember(dest => dest.Topping, opt => opt.MapFrom(src => src.ToppingDrink.Topping))
                .ReverseMap();
        }
    }
}
