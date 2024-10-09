using BussinessObjects.DTO;
using DataAccess.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using PRM392_CafeOnline_BE_API.ResponseType;
using Repositories.Interface;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly ILogger<AuthController> _logger;
        private readonly IUserRepository _userRepository;
        public AuthController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterRequest request)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var existingUser = await _userRepository.GetUserByEmailAsync(request.Email);
            if (existingUser != null)
            {
                return Conflict(new JsonResponse<string>(null, 400, "Email already exists"));
            }

            var newUser = new TblUser
            {

                Username = request.Username,
                Email = request.Email,
                Password = HashPassword(request.Password),
                Phone = request.Phone,
                Address = request.Address,
                CreatedDate = DateTime.Now,
                UpdatedDate = DateTime.Now
            };

            await _userRepository.AddUserAsync(newUser);

            return Ok(new JsonResponse<string>(null, 200, "User registered successfully"));
        }

        private string HashPassword(string password)
        {
            return BCrypt.Net.BCrypt.HashPassword(password);
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginRequestDTO request)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }


            var user = await _userRepository.GetUserByEmailAsync(request.Email);
            if (user == null)
            {
                return Unauthorized(new JsonResponse<string>(null, 401, "Invalid email or password"));
            }


            bool isPasswordValid;

            // Nếu mật khẩu đã được mã hóa bằng BCrypt
            if (user.Password.StartsWith("$2a$") || user.Password.StartsWith("$2b$") || user.Password.StartsWith("$2y$"))
            {
                isPasswordValid = BCrypt.Net.BCrypt.Verify(request.Password, user.Password);
            }
            else
            {
                // Môi trường phát triển: nếu mật khẩu là plain text, so sánh trực tiếp
                isPasswordValid = request.Password == user.Password;
            }

            // Nếu mật khẩu không hợp lệ
            if (!isPasswordValid)
            {
                return Unauthorized(new JsonResponse<string>(null, 401, "Invalid email or password"));
            }


            var token = GenerateJwtToken(user);


            return Ok(new JsonResponse<string>(token, 200, "Login successful"));
        }


        private string GenerateJwtToken(TblUser user)
        {
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("YourSecretKeyHere")); // Đặt secret key mạnh
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);

            var claims = new[]
            {
            new Claim(JwtRegisteredClaimNames.Email, user.Email),
             new Claim(JwtRegisteredClaimNames.Name, user.Username),

        };

            var token = new JwtSecurityToken(
                issuer: "CoffeeOnline.com",
                audience: "CoffeeOnline.com",
                claims: claims,
                expires: DateTime.Now.AddMinutes(30),
                signingCredentials: credentials);

            return new JwtSecurityTokenHandler().WriteToken(token);
        }

        [HttpPost("logout")]
        public IActionResult Logout()
        {
            return Ok(new JsonResponse<string>(null, 200, "Login successful"));
        }
    }
}


