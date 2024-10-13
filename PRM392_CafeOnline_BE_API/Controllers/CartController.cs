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
        public async Task<IActionResult> AddToCart([FromBody] ToppingDrinkRequestDTO toppingDrinkRequestDTO)
        {
            var cartItem = await _cartService.AddToCart(toppingDrinkRequestDTO);
            return Ok(new JsonResponse<CartToppingDrinkDTO>(cartItem, 200, "Add to cart successfully"));
        }
        [HttpGet("{cartId}")]
        public async Task<IActionResult> GetCartById(int cartId)
        {
            var cart = await _cartService.GetCartById(cartId);
            return Ok(new JsonResponse<CartDTO>(cart, 200, "Get cart successfully"));
        }
        [HttpPut("{cartToppingDrinkId}")]
        public async Task<IActionResult> UpdateCartItem(int cartToppingDrinkId, [FromBody] int quantity)
        {
            var cartItem = await _cartService.UpdateQuantity(cartToppingDrinkId, quantity);
            return Ok(new JsonResponse<CartToppingDrinkDTO>(cartItem, 200, "Update successfully"));
        }
        [HttpDelete("{cartToppingDrinkId}")]
        public async Task<IActionResult> RemoveItem(int cartToppingDrinkId)
        {
            await _cartService.RemoveFromCart(cartToppingDrinkId);
            return NoContent();
        }
    }
}
