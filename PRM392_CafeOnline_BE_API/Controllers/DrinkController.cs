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

        [HttpGet("filter")]
        public async Task<IActionResult> FilterDrinksAsync([FromQuery] string? categoryName, [FromQuery] decimal? minPrice,[FromQuery] decimal? maxPrice,[FromQuery] DateTime? startDate,[FromQuery] DateTime? endDate, [FromQuery] string? size)
        {
            try
            {
                // Gọi service để thực hiện logic lọc bất đồng bộ
                var filteredDrinks = await _drinkRepository.FilterDrinksAsync(categoryName, minPrice, maxPrice, startDate, endDate, size);

                // Kiểm tra nếu danh sách kết quả rỗng hoặc null
                if (filteredDrinks == null || !filteredDrinks.Any())
                {
                    return NotFound("No drinks found matching the filter criteria.");
                }

                var result = filteredDrinks.Select(d => new DrinkDTO
                {
                    Id = d.Id,
                    Name = d.Name,
                    Description = d.Description,
                    Price = d.Price,
                    CreatedDate = d.CreatedDate,
                    UpdatedDate = d.UpdatedDate,
                    Image = d.Image,
                    Size = d.Size,
                    CategoryName = d.Category?.Name
                }).ToList();

                return Ok(new JsonResponse<List<DrinkDTO>>(result, 200, "Get Drink List After Filter Sucessfully"));
            }
            catch (Exception ex)
            {
                // Log lỗi nếu có (tùy chọn: có thể log lại với Serilog, NLog, hoặc bất kỳ framework logging nào)
                Console.WriteLine($"An error occurred: {ex.Message}");

                // Trả về mã lỗi 500 (Internal Server Error) và thông báo lỗi
                return StatusCode(500, "An error occurred while processing your request.");
            }
        }

    }
}
