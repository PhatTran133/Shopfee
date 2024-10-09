
using BussinessObjects.DTO;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class OtpController : ControllerBase
    {


        private readonly IOtpService _otpService;
        private readonly IUserService _userService;

        public OtpController(IOtpService otpService, IUserService userService)
        {
            _otpService = otpService;
            _userService = userService;
        }

        [HttpPost("verify-otp-register")]
        public async Task<IActionResult> VerifyOtpRegister([FromBody] VerifyOtpRequestDTO requestDTO)
        {
            try
            {
                var otp = await _otpService.GetOtpByEmailAsync(requestDTO.Email);
                if (otp == null || otp.Code != requestDTO.Code || otp.ExpiryTime < DateTime.UtcNow)
                {
                    return BadRequest(new JsonResponse<string>(null, 400, "Mã OTP không hợp lệ hoặc đã hết hạn."));
                }

                var user = await _userService.GetUserByUnverifiedEmailAsync(requestDTO.Email);
                if (user == null)
                {
                    return NotFound(new JsonResponse<string>(null, 400, "Không tìm thấy tài khoản người dùng."));
                }

                await _userService.VerifyUserAccountAsync(user.Id);

                await _otpService.DeleteOtpAsync(otp.Code);

                return Ok(new JsonResponse<int>(user.Id, 200, "Xác nhận thành công"));
            }
            catch (Exception ex)
            {
                if (ex.Message != null)
                    return BadRequest(new JsonResponse<string>(null, 400, ex.Message));

                return StatusCode(500, new JsonResponse<string>(null, 500, "Internal Server Error"));
            }

        }



    }
}
