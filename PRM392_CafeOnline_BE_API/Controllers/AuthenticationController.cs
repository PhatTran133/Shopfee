﻿using BussinessObjects.DTO;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PRM392_CafeOnline_BE_API.ResponseType;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories.Interface;

namespace PRM392_CafeOnline_BE_API.Controllers
{
    [Route("api/authentication")]
    [ApiController]
    public class AuthenticationController : ControllerBase
    {
        private readonly IForgotPasswordService _forgotPasswordService;
        public AuthenticationController(IForgotPasswordService forgotPasswordService)
        {
            _forgotPasswordService = forgotPasswordService;
        }

        [HttpPost("forgot-password")]
        public async Task<IActionResult> ForgotPassword([FromBody] ForgotPasswordDTO forgotPasswordDTO)
        {
            await _forgotPasswordService.GenerateResetCodeAsync(forgotPasswordDTO.Email);
            return Ok(new JsonResponse<string>(null, 200, $"Password Reset Code has been sent to email {forgotPasswordDTO.Email}"));
        }

        [HttpPost("verify-reset-code")]
        public async Task<IActionResult> VerifyCode([FromBody] VerifyCodeDTO verifyCodeDTO)
        {
            var isValid = await _forgotPasswordService.VerifyResetCodeAsync(verifyCodeDTO.Email, verifyCodeDTO.Code);
            if (!isValid)
            {
                return BadRequest(new JsonResponse<string>(null, 400, "Invalid code"));
            }
            return Ok(new JsonResponse<string>(null, 200, $"Code {verifyCodeDTO.Code} verified successfully"));
        }

        [HttpPut("reset-password")]
        public async Task<IActionResult> ResetPassword([FromBody] ResetPasswordDTO resetPasswordDTO)
        {
            await _forgotPasswordService.ResetPasswordAsync(resetPasswordDTO.Email, resetPasswordDTO.Code, resetPasswordDTO.NewPassword);
            return Ok(new JsonResponse<ResetPasswordDTO>(resetPasswordDTO, 200, "Password Reset Successfully"));
        }
    }
}
