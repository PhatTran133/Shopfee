using DataAccess.DTO;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly IUserRepository _userRepository;
        public UsersController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        [HttpGet("{userId}")]
        public async Task<IActionResult> GetUser(int userId)
        {
            if (string.IsNullOrEmpty(Convert.ToString(userId)))
            {
                return BadRequest();
            }

            var user = await _userRepository.GetTblUser(userId);
            if (user == null)
            {
                return NotFound();
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
            
            return Ok(userResponse);
        }
    }
}
