using BussinessObjects.DTO;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class AdditionalInformationController : ControllerBase
    {
        private readonly IAdditionalInformationService _service;

        public AdditionalInformationController(IAdditionalInformationService service)
        {
            _service = service;
        }

        // API thêm địa chỉ cho user
        [HttpPost("add-address")]
        public async Task<IActionResult> AddAddress(int userId, [FromBody] AddAddressDto addAddressDto)
        {
            if (ModelState.IsValid)
            {
                try
                {
                    await _service.AddAddressAsync(userId, addAddressDto.Name, addAddressDto.Phone, addAddressDto.Address);
                    var response = new JsonResponse<string>(null, 200, "Address added successfully");
                    return Ok(response);
                }
                catch (Exception ex)
                {
                    var errorResponse = new JsonResponse<string>(null, 500, $"An error occurred: {ex.Message}");
                    return StatusCode(500, errorResponse);
                }
            }

            var invalidResponse = new JsonResponse<string>(null, 400, "Invalid data");
            return BadRequest(invalidResponse);
        }

        [HttpDelete("delete-address/{addressId}")]
        public async Task<IActionResult> DeleteAddress(int userId, int addressId)
        {
            try
            {
                await _service.DeleteAddressAsync(userId, addressId);
                var response = new JsonResponse<string>(null, 200, "Address deleted successfully");
                return Ok(response);
            }
            catch (Exception ex)
            {
                var errorResponse = new JsonResponse<string>(null, 500, $"An error occurred: {ex.Message}");
                return StatusCode(500, errorResponse);
            }
        }


        [HttpGet("get-all-addresses")]
        public async Task<IActionResult> GetAllAddresses(int userId)
        {
            try
            {
                var addresses = await _service.GetAllAddressesByUserIdAsync(userId);
                var response = new JsonResponse<List<AddAddressDto>>(addresses, 200, "Addresses retrieved successfully");
                return Ok(response);
            }
            catch (Exception ex)
            {
                var errorResponse = new JsonResponse<string>(null, 500, $"An error occurred: {ex.Message}");
                return StatusCode(500, errorResponse);
            }
        }
    }
}
