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
        [HttpPost]
        public async Task<IActionResult> AddToCart([FromBody] AddToCartRequestDTO toppingDrinkRequestDTO)
        {
            try
            {
                var cartItem = await _cartService.AddToCart(toppingDrinkRequestDTO);
                if(cartItem != null) 
                     return Ok(new JsonResponse<string>("Add to cart successfully", 200, "Add to cart successfully"));
                return BadRequest(new JsonResponse<string>(null, 400, "Failed to add to cart"));
            }
            catch(Exception ex)
            {
                if (ex.Message != null)
                {
                    return BadRequest(new JsonResponse<string>(ex.Message, 400, ex.Message));
                }
                return StatusCode(500, ex.Message);
            }
        }
        [HttpGet("{userId}")]
        public async Task<IActionResult> GetCartByUserId(int userId)
        {
            try
            {
                var cart = await _cartService.GetCartByUserId(userId);
                return Ok(new JsonResponse<CartDTO>(cart, 200, "Get cart successfully"));
            }catch(Exception ex)
            {
                if (ex.Message != null)
                {
                    return BadRequest(new JsonResponse<string>(ex.Message, 400, ex.Message));
                }
                return StatusCode(500, ex.Message);
            }
        }
        [HttpPut("{cartToppingDrinkId}")]
        public async Task<IActionResult> UpdateCartItem(int cartToppingDrinkId, [FromBody] UpdateCartItemRequestDTO updateCartItemRequestDTO)
        {
            try
            {
                var cartItem = await _cartService.UpdateCartItem(cartToppingDrinkId, updateCartItemRequestDTO);
                return Ok(new JsonResponse<CartToppingDrinkDTO>(cartItem, 200, "Update successfully"));
            }
            catch (Exception ex)
            {
                if (ex.Message != null)
                {
                    return BadRequest(new JsonResponse<string>(ex.Message, 400, ex.Message));
                }
                return StatusCode(500, ex.Message);
            }
        }
        [HttpDelete("{cartToppingDrinkId}")]
        public async Task<IActionResult> RemoveItem(int cartToppingDrinkId)
        {
            try
            {
                await _cartService.RemoveFromCart(cartToppingDrinkId);
                return NoContent();
            }catch(Exception ex)
            {
                if (ex.Message != null)
                {
                    return BadRequest(new JsonResponse<string>(ex.Message, 400, ex.Message));
                }
                return StatusCode(500, ex.Message);
            }
        }
    }
}
