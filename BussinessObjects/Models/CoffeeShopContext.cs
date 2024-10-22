using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.Extensions.Configuration;

namespace BussinessObjects.Models
{
    public partial class CoffeeShopContext : DbContext
    {
        public CoffeeShopContext()
        {
        }

        public CoffeeShopContext(DbContextOptions<CoffeeShopContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Cart> Carts { get; set; } = null!;
        public virtual DbSet<CartItem> CartItems { get; set; } = null!;
        public virtual DbSet<CartItemTopping> CartItemToppings { get; set; } = null!;
        public virtual DbSet<Category> Categories { get; set; } = null!;
        public virtual DbSet<Drink> Drinks { get; set; } = null!;
        public virtual DbSet<Otp> Otps { get; set; } = null!;
        public virtual DbSet<Payment> Payments { get; set; } = null!;
        public virtual DbSet<TblNotification> TblNotifications { get; set; } = null!;
        public virtual DbSet<TblOrder> TblOrders { get; set; } = null!;
        public virtual DbSet<OrderItem> OrderItems { get; set; }
        public virtual DbSet<OrderItemTopping> OrderItemToppings { get; set; } = null!;
        public virtual DbSet<TblUser> TblUsers { get; set; } = null!;
        public virtual DbSet<Topping> Toppings { get; set; } = null!;

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
       => optionsBuilder.UseNpgsql(GetConnectionString());

        private string GetConnectionString()
        {
            IConfiguration config = new ConfigurationBuilder()
                 .SetBasePath(Directory.GetCurrentDirectory())
                        .AddJsonFile("appsettings.json", true, true)
                        .Build();
            var strConn = config["ConnectionStrings:PostGresServer"];

            return strConn;
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            //modelBuilder.ApplyConfiguration(new RoleConfiguration());
            ConfigureModel(modelBuilder);
            BuildCartModel(modelBuilder);

            BuildOrderModel(modelBuilder);

            OnModelCreatingPartial(modelBuilder);
        }

        private static void BuildOrderModel(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<OrderItem>()
           .HasOne(oi => oi.Order)
           .WithMany(o => o.OrderItems)
           .HasForeignKey(oi => oi.OrderId);

            modelBuilder.Entity<OrderItem>()
                .HasOne(oi => oi.Drink)
                .WithMany(d => d.OrderItems)
                .HasForeignKey(oi => oi.DrinkId);

            modelBuilder.Entity<OrderItemTopping>()
                .HasOne(oit => oit.OrderItem)
                .WithMany(oi => oi.OrderItemToppings)
                .HasForeignKey(oit => oit.OrderItemId);

            modelBuilder.Entity<OrderItemTopping>()
                .HasOne(oit => oit.Topping)
                .WithMany(t => t.OrderItemToppings)
                .HasForeignKey(oit => oit.ToppingId);
        }

        private static void BuildCartModel(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<CartItem>()
                            .HasOne(ci => ci.Cart)
                            .WithMany(c => c.CartItems)
                            .HasForeignKey(c => c.CartId);

            modelBuilder.Entity<CartItem>()
                .HasOne(ci => ci.Drink)
                .WithMany(d => d.CartItems)
                .HasForeignKey(c => c.DrinkId);

            modelBuilder.Entity<CartItemTopping>()
                .HasOne(cit => cit.CartItem)
                .WithMany(ci => ci.CartItemToppings)
                .HasForeignKey(c => c.CartItemId);

            modelBuilder.Entity<CartItemTopping>()
                .HasOne(cit => cit.Topping)
                .WithMany(t => t.CartItemToppings)
                .HasForeignKey(c => c.ToppingId);
        }

        private void ConfigureModel(ModelBuilder modelBuilder)
        {


        }
        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
