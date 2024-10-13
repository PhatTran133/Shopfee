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
            CreateMap<ToppingDrinkRequestDTO, CartToppingDrink>();
            CreateMap<CartToppingDrink, CartToppingDrinkDTO>()
                .ForMember(dest => dest.Drink, opt => opt.MapFrom(src => src.ToppingDrink.Drink))
                .ForMember(dest => dest.Topping, opt => opt.MapFrom(src => src.ToppingDrink.Topping))
                .ReverseMap();
            CreateMap<Drink, DrinkDTO>().ReverseMap();
            CreateMap<Topping, ToppingDTO>().ReverseMap();
        }


    }
}
