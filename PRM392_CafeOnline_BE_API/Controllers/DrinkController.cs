using Microsoft.AspNetCore.Mvc;
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
                return BadRequest("Name cannot be empty.");
            }

            var drinks = await _drinkRepository.SearchDrinksByNameAsync(name);
            return Ok(drinks);
        }
    }
}
