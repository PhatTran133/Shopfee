using BussinessObjects.DTO;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/carts")]
    [ApiController]
    public class CartController : ControllerBase
    {
        private readonly ICartService _cartService;
        public CartController(ICartService cartService)
        {
            _cartService = cartService;
        }

        [HttpGet]
        public async Task<IActionResult> GetCarts()
        {
            var cartDTOs = await _cartService.GetCartDTOsAsync();
            return Ok(new JsonResponse<IEnumerable<CartDTO>>(cartDTOs, 200, "Get successfully"));
        }

        [HttpPost("{cartId}")]
        public async Task<IActionResult> AddToCartAsync(int cartId, [FromBody]CartItemDTO cartItemDTO)
        {
            try
            {
                await _cartService.AddToCartAsync(cartId, cartItemDTO);
                return Ok(new JsonResponse<CartItemDTO>(cartItemDTO, 200, "Drink added to cart successfully"));
            }catch(Exception ex)
            {
                return StatusCode(500, new JsonResponse<string>("error", 500, ex.Message));
            }
        }

        [HttpPut("{cartId}")]
        public async Task<IActionResult> UpdateCartItemQuantity(int cartId, [FromQuery] int drinkId, [FromQuery] int quantity)
        {
            try
            {
                var cartItemDto = await _cartService.UpdateCartItemQuantityAsync(cartId, drinkId, quantity);
                return Ok(new JsonResponse<CartItemDTO>(cartItemDto, 200, "Quantity updated successfully"));
            }catch(Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [HttpDelete("cartId")]
        public async Task<IActionResult> DeleteCartItem(int cartId, [FromQuery] int drinkId)
        {
            try
            {
                await _cartService.DeleteCartItemAsync(cartId, drinkId);
                return NoContent();
            }catch(Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }
    }
}
