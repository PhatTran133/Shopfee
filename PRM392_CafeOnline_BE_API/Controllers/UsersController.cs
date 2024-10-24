using BussinessObjects.DTO;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly IUserService _userService;
        private readonly IOtpService _otpService;
        private readonly IUserRepository _userRepository;
        public UsersController(IUserRepository userRepository, IUserService userService, IOtpService otpService)
        {
            _userRepository = userRepository;
            _userService = userService;
            _otpService = otpService;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterRequest requestDTO)
        {
            try
            {
                var email = requestDTO.Email;
                if (!email.EndsWith("@gmail.com", StringComparison.OrdinalIgnoreCase))
                {
                    return BadRequest(new JsonResponse<string>(null, 400, "Email phải thuộc miền @gmail.com"));
                }
                var isCreated = await _userService.CreateUserForOtp(requestDTO);
                if (!isCreated) throw new Exception("Không thể tạo tài khoản");

                var generateOtpAndSendMail = await _otpService.GenerateOtpAndSendEmail(requestDTO.Email);
                if (!generateOtpAndSendMail) throw new Exception("Lỗi khi tạo hoặc gửi Otp");

                if (!isCreated)
                {
                    return BadRequest("Không thể tạo tài khoản.");
                }
                return Ok(new JsonResponse<string>(null, 200, "Đăng ký thành công. Vui lòng kiểm tra email để xác nhận OTP."));
            }
            catch (Exception ex)
            {
                if (ex.Message != null)
                    return BadRequest(new JsonResponse<string>(null, 400, ex.Message));

                return StatusCode(500, new JsonResponse<string>(null, 500, "Internal Server Error"));
            }
        }


        [HttpPost("login")]
        public async Task<ActionResult<JsonResponse<UserDTO>>> Login(LoginRequestDTO requestDTO)
        
        {
            try
            {
                var email = requestDTO.Email;
                if (!email.EndsWith("@gmail.com", StringComparison.OrdinalIgnoreCase))
                {
                    return BadRequest(new JsonResponse<string>(null, 400, "Email phải thuộc miền @gmail.com"));
                }

                var result = await _userService.LoginAsync(requestDTO);
                return Ok(new JsonResponse<UserDTO>(result, 200, "Login successfully"));
            }
            catch (Exception ex)
            {
                if (ex.Message != null)
                    return BadRequest(new JsonResponse<string>(null, 400, ex.Message));

                return StatusCode(500, new JsonResponse<string>(null, 500, "Internal Server Error"));
            }
        }

        [HttpGet("{userId}")]
        public async Task<IActionResult> GetUser(int userId)
        {
            if (string.IsNullOrEmpty(Convert.ToString(userId)))
            {
                return BadRequest(new JsonResponse<string>(null, 400, "UserId cannot be null"));
            }

            var user = await _userRepository.GetTblUser(userId);
            if (user == null)
            {
                return NotFound(new JsonResponse<string>(null, 404, "User is not found"));
            }

            var userResponse = new UserDTO
            {
                Id = userId,
                Address = user.Address,
                CreatedDate = user.CreatedDate,
                Email = user.Email,
                Password = user.Password,
                Phone = user.Phone,
                UpdatedDate = user.UpdatedDate,
                Username = user.Username,
            };

            return Ok(new JsonResponse<UserDTO>(userResponse, 200, "Get User Sucessfully"));
        }



    }
}



