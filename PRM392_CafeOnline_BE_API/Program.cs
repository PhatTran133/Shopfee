using BussinessObjects.Models;
using DataAccess;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using PRM392_CafeOnline_BE_API.Configurations;
using PRM392_CafeOnline_BE_API.Services;
using PRM392_CafeOnline_BE_API.Services.Interfaces;
using Repositories;
using Repositories.Interface;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddDbContext<CoffeeShopContext>();
builder.Services.AddDbContext<InMemoryDbContext>(options =>
	options.UseInMemoryDatabase("InMemoryDb"));

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddAutoMapper(AppDomain.CurrentDomain.GetAssemblies());
builder.Services.AddHostedService<DeleteExpiredUsersService>();
builder.Services.AddAuthentication(options =>
{
	options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
	options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
})
.AddJwtBearer(options =>
{
	options.TokenValidationParameters = new TokenValidationParameters
	{
		ValidateIssuer = true,
		ValidateAudience = true,
		ValidateLifetime = true,
		ValidateIssuerSigningKey = true,
		ValidIssuer = "CoffeeOnline.com",
		ValidAudience = "CoffeeOnline.com",
		IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("mayemixeneldnhowyytjohgadwosqogo")) // Đặt secret key mạnh
	};
});
builder.Services.AddCors(options =>
{
	options.AddPolicy("AllowAllOrigins",
	builder =>
	{
		builder.WithOrigins("*")
			   .AllowAnyHeader()
			   .AllowAnyMethod();
	});
});

// Register Services
builder.Services.AddFluentEmailExtension(builder.Configuration);
builder.Services.AddScoped<DrinkDAO>();
builder.Services.AddScoped<DrinkRepository>();
builder.Services.AddScoped<IDrinkRepository, DrinkRepository>();
builder.Services.AddScoped<IUserRepository, TblUserRepository>();
builder.Services.AddScoped<IOtpRepository, OtpRepository>();
builder.Services.AddScoped<IOtpService, OtpService>();
builder.Services.AddScoped<IEmailService, EmailService>();
builder.Services.AddScoped<IForgotPasswordService, ForgotPasswordService>();

builder.Services.AddScoped<ICartItemRepository, CartItemRepository>();
builder.Services.AddScoped<ICartItemToppingRepository, CartItemToppingRepository>();
builder.Services.AddScoped<ICartRepository, CartRepository>();
builder.Services.AddScoped<ICartService, CartService>();


builder.Services.AddScoped<IToppingRepository, ToppingRepository>();

builder.Services.AddScoped<IOrderRepository, TblOrderRepository>();
builder.Services.AddScoped<IOrderItemRepository, OrderItemRepository>();
builder.Services.AddScoped<IOrderItemToppingRepository, OrderItemToppingRepository>();
builder.Services.AddScoped<IOrderService, OrderService>();

builder.Services.AddScoped<IUserRepository, TblUserRepository>();
builder.Services.AddScoped<IUserService, UserService>();

builder.Services.AddScoped<IDrinkRepository, DrinkRepository>();
builder.Services.AddScoped<IDrinkService, DrinkService>();

builder.Services.AddScoped<IToppingService, ToppingService>();
builder.Services.AddScoped<IToppingRepository, ToppingRepository>();

builder.Services.AddTransient<IPaymentService, PaymentService>();
builder.Services.AddTransient<IVnPayService, VNPayService>();

builder.Services.AddTransient<INotificationRepository, TblNotificationRepository>();
builder.Services.AddScoped<INotificationService, NotificationService>();

//builder.Services.AddScoped<IAdditionalInformationRepository, AdditionalInformationRepository>();
builder.Services.AddScoped<IAdditionalInformationService, AdditionalInformationService>();

var app = builder.Build();

AppContext.SetSwitch("Npgsql.EnableLegacyTimestampBehavior", true);
// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
	app.UseDeveloperExceptionPage();
	app.UseSwagger();
	app.UseSwaggerUI();
}
else
{
	app.UseSwagger();
	app.UseSwaggerUI(c =>
	{
		c.SwaggerEndpoint("/swagger/v1/swagger.json", "KooHee API V1");
		c.RoutePrefix = string.Empty;
	});
}

app.UseHttpsRedirection();
app.UseCors("AllowAllOrigins");
app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

app.Run("http://0.0.0.0:7079");
