using BussinessObjects.DTO;
using DataAccess.DTO;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using Repositories;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DrinkController : ControllerBase
    {
        private readonly DrinkRepository _drinkRepository;

        public DrinkController(DrinkRepository drinkRepository)
        {
            _drinkRepository = drinkRepository;
        }
        [HttpGet("search")]
        public async Task<IActionResult> SearchDrinks([FromQuery] string name)
        {
            if (string.IsNullOrEmpty(name))
            {
                return BadRequest(new JsonResponse<string>(null, 400, "Name cannot be empty."));
            }

            var drinks = await _drinkRepository.SearchDrinksByNameAsync(name);

            if (drinks == null)
            {
                return NoContent();
            }
            // Trả về một đối tượng ẩn danh trực tiếp
            var result = drinks.Select(d => new DrinkDTO
            {
                Id = d.Id,
                Name = d.Name,
                Description = d.Description,
                Price = d.Price,
                CreatedDate = d.CreatedDate,
                UpdatedDate = d.UpdatedDate,
                Image = d.Image,
                Size = d.Size,
                CategoryName = d.Category?.Name // Lấy tên Category
            }).ToList();

            return Ok(new JsonResponse<List<DrinkDTO>>(result, 200, "Get Drink List Sucessfully"));  // Trả về danh sách đối tượng ẩn danh
        }
    }
}
