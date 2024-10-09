using BussinessObjects.DTO;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DrinkController : ControllerBase
    {
        private readonly IDrinkService _drinkService;
        private readonly DrinkRepository _drinkRepository;

        public DrinkController(DrinkRepository drinkRepository, IDrinkService drinkService)
        {
            _drinkRepository = drinkRepository;
            _drinkService = drinkService;
        }
        [HttpGet("search")]
        public async Task<IActionResult> SearchDrinks([FromQuery] int id)
        {
            if (id == null)
            {
                return BadRequest(new JsonResponse<string>(null, 400, "Id cannot be empty."));
            }

            var drinks = await _drinkRepository.SearchDrinksByIdAsync(id);

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
        public async Task<IActionResult> FilterDrinksAsync([FromQuery] string? name, [FromQuery] string? categoryName, [FromQuery] decimal? minPrice, [FromQuery] decimal? maxPrice, [FromQuery] DateTime? startDate, [FromQuery] DateTime? endDate, [FromQuery] string? size)
        {
            try
            {
                // Gọi service để thực hiện logic lọc bất đồng bộ
                var filteredDrinks = await _drinkRepository.FilterDrinksAsync(name, categoryName, minPrice, maxPrice, startDate, endDate, size);

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

        [HttpGet("drinks-by-category")]
        public async Task<IActionResult> GetDrinksByCategoryName([FromQuery] string categoryName)
        {
            var result = await _drinkService.GetDrinksByCategoryName(categoryName);
            if (result == null || !result.Any())
            {
                return NotFound($"No drinks found for category '{categoryName}'.");
            }

            return Ok(result);
        }

        [HttpGet("{drinkId}/details")]
        public async Task<IActionResult> GetDrinkDetail(int drinkId)
        {
            var drinkDetail = await _drinkService.GetDrinkDetailAsync(drinkId);
            if (drinkDetail == null)
            {
                return NotFound(); // Trả về 404 nếu không tìm thấy đồ uống
            }
            return Ok(drinkDetail); // Trả về thông tin chi tiết đồ uống
        }

    }
}
