﻿using BussinessObjects.DTO;
using BussinessObjects.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repositories.Interface
{
    public interface IToppingRepository
    {
        Task<Topping?> GetToppingByIdAsync(int id);
        Task<List<Topping>> GetAllTopping();
    }
}
