using BussinessObjects.DTO;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ToppingController : ControllerBase
    {
        private readonly IToppingService _toppingService;

        public ToppingController(IToppingService toppingService)
        {
            _toppingService = toppingService;
        }
        [HttpGet]
        public async Task<IActionResult> GetAllTopping()
        {
            var toppings = await _toppingService.GetAllTopping();

            if (toppings == null)
            {
                return NoContent();
            }
            // Trả về một đối tượng ẩn danh trực tiếp
            var result = toppings.Select(d => new ToppingDTO
            {
                Id = d.Id,
                Name = d.Name,
                Price = d.Price,
                CreatedDate = DateTime.UtcNow,
                UpdatedDate = d.UpdatedDate,
            }).ToList();

            return Ok(new JsonResponse<List<ToppingDTO>>(result, 200, "Get Topping List Sucessfully"));  // Trả về danh sách đối tượng ẩn danh
        }
    }
}
