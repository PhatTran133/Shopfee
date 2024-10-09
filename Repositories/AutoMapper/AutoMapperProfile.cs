using AutoMapper;
using BussinessObjects.DTO;
using DataAccess.Models;
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

        }


    }
}
