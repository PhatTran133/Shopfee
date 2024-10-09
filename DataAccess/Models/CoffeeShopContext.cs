using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace DataAccess.Models
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
        public virtual DbSet<CartToppingDrink> CartToppingDrinks { get; set; } = null!;
        public virtual DbSet<Category> Categories { get; set; } = null!;
        public virtual DbSet<Drink> Drinks { get; set; } = null!;
        public virtual DbSet<DrinkTopping> DrinkToppings { get; set; } = null!;
        public virtual DbSet<OrderToppingDrink> OrderToppingDrinks { get; set; } = null!;
        public virtual DbSet<Otp> Otps { get; set; } = null!;
        public virtual DbSet<Payment> Payments { get; set; } = null!;
        public virtual DbSet<TblNotification> TblNotifications { get; set; } = null!;
        public virtual DbSet<TblOrder> TblOrders { get; set; } = null!;
        public virtual DbSet<TblUser> TblUsers { get; set; } = null!;
        public virtual DbSet<Topping> Toppings { get; set; } = null!;

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                //    optionsBuilder.UseSqlServer("Server=(local);Uid=sa;Pwd=12345;Database=CoffeeShop;TrustServerCertificate=True");
                optionsBuilder.UseSqlServer("Server=database-1.cd82m0owep6n.ap-southeast-2.rds.amazonaws.com,1433;Database = CoffeeShop;Uid=admin; Pwd = Helloworld12345; Trusted_Connection = True");

            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Cart>(entity =>
            {
                entity.ToTable("Cart");

                entity.HasIndex(e => e.UserId, "IX_Cart_userId");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.DeletedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("deletedDate");

                entity.Property(e => e.TotalPrice)
                    .HasColumnType("decimal(10, 2)")
                    .HasColumnName("totalPrice");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");

                entity.Property(e => e.UserId).HasColumnName("userId");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Carts)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("FK_Cart_tblUser");
            });

            modelBuilder.Entity<CartToppingDrink>(entity =>
            {
                entity.ToTable("CartToppingDrink");

                entity.HasIndex(e => e.CartId, "IX_CartToppingDrink_cartId");

                entity.HasIndex(e => e.ToppingDrinkId, "IX_CartToppingDrink_toppingDrinkId");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.CartId).HasColumnName("cartId");

                entity.Property(e => e.Quantity).HasColumnName("quantity");

                entity.Property(e => e.ToppingDrinkId).HasColumnName("toppingDrinkId");

                entity.HasOne(d => d.Cart)
                    .WithMany(p => p.CartToppingDrinks)
                    .HasForeignKey(d => d.CartId)
                    .HasConstraintName("FK__CartToppi__cartI__38996AB5");

                entity.HasOne(d => d.ToppingDrink)
                    .WithMany(p => p.CartToppingDrinks)
                    .HasForeignKey(d => d.ToppingDrinkId)
                    .HasConstraintName("FK__CartToppi__toppi__398D8EEE");
            });

            modelBuilder.Entity<Category>(entity =>
            {
                entity.ToTable("Category");

                entity.Property(e => e.Id)
                    .ValueGeneratedNever()
                    .HasColumnName("id");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.Name)
                    .HasMaxLength(100)
                    .IsUnicode(false)
                    .HasColumnName("name");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");
            });

            modelBuilder.Entity<Drink>(entity =>
            {
                entity.ToTable("Drink");

                entity.HasIndex(e => e.CategoryId, "IX_Drink_categoryId");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.CategoryId).HasColumnName("categoryId");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.Description)
                    .HasColumnType("text")
                    .HasColumnName("description");

                entity.Property(e => e.Image)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("image");

                entity.Property(e => e.IsDeleted).HasColumnName("isDeleted");

                entity.Property(e => e.Name)
                    .HasMaxLength(100)
                    .IsUnicode(false)
                    .HasColumnName("name");

                entity.Property(e => e.Price)
                    .HasColumnType("decimal(10, 2)")
                    .HasColumnName("price");

                entity.Property(e => e.Size)
                    .HasMaxLength(20)
                    .IsUnicode(false)
                    .HasColumnName("size");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");

                entity.HasOne(d => d.Category)
                    .WithMany(p => p.Drinks)
                    .HasForeignKey(d => d.CategoryId)
                    .HasConstraintName("FK__Drink__categoryI__3A81B327");
            });

            modelBuilder.Entity<DrinkTopping>(entity =>
            {
                entity.ToTable("DrinkTopping");

                entity.HasIndex(e => e.DrinkId, "IX_DrinkTopping_drinkId");

                entity.HasIndex(e => e.ToppingId, "IX_DrinkTopping_toppingId");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.DrinkId).HasColumnName("drinkId");

                entity.Property(e => e.ToppingId).HasColumnName("toppingId");

                entity.HasOne(d => d.Drink)
                    .WithMany(p => p.DrinkToppings)
                    .HasForeignKey(d => d.DrinkId)
                    .HasConstraintName("FK__DrinkTopp__drink__3B75D760");

                entity.HasOne(d => d.Topping)
                    .WithMany(p => p.DrinkToppings)
                    .HasForeignKey(d => d.ToppingId)
                    .HasConstraintName("FK__DrinkTopp__toppi__3C69FB99");
            });

            modelBuilder.Entity<OrderToppingDrink>(entity =>
            {
                entity.ToTable("OrderToppingDrink");

                entity.HasIndex(e => e.OrderId, "IX_OrderToppingDrink_orderId");

                entity.HasIndex(e => e.ToppingDrinkId, "IX_OrderToppingDrink_toppingDrinkId");

                entity.Property(e => e.Id)
                    .ValueGeneratedNever()
                    .HasColumnName("id");

                entity.Property(e => e.OrderId).HasColumnName("orderId");

                entity.Property(e => e.Quantity).HasColumnName("quantity");

                entity.Property(e => e.ToppingDrinkId).HasColumnName("toppingDrinkId");

                entity.HasOne(d => d.Order)
                    .WithMany(p => p.OrderToppingDrinks)
                    .HasForeignKey(d => d.OrderId)
                    .HasConstraintName("FK__OrderTopp__order__3D5E1FD2");

                entity.HasOne(d => d.ToppingDrink)
                    .WithMany(p => p.OrderToppingDrinks)
                    .HasForeignKey(d => d.ToppingDrinkId)
                    .HasConstraintName("FK__OrderTopp__toppi__3E52440B");
            });

            modelBuilder.Entity<Payment>(entity =>
            {
                entity.ToTable("Payment");

                entity.HasIndex(e => e.OrderId, "IX_Payment_orderId");

                entity.Property(e => e.Id)
                    .ValueGeneratedNever()
                    .HasColumnName("id");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.DeletedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("deletedDate");

                entity.Property(e => e.Detail)
                    .HasMaxLength(100)
                    .IsUnicode(false)
                    .HasColumnName("detail");

                entity.Property(e => e.OrderId).HasColumnName("orderId");

                entity.Property(e => e.Type)
                    .HasMaxLength(50)
                    .IsUnicode(false)
                    .HasColumnName("type");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");

                entity.HasOne(d => d.Order)
                    .WithMany(p => p.Payments)
                    .HasForeignKey(d => d.OrderId)
                    .HasConstraintName("FK__Payment__orderId__3F466844");
            });

            modelBuilder.Entity<TblNotification>(entity =>
            {
                entity.ToTable("tblNotification");

                entity.HasIndex(e => e.UserId, "IX_tblNotification_userId");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Content)
                    .HasMaxLength(100)
                    .IsUnicode(false)
                    .HasColumnName("content");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.DeletedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("deletedDate");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");

                entity.Property(e => e.UserId).HasColumnName("userId");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.TblNotifications)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("FK_tblNotification_tblUser");
            });

            modelBuilder.Entity<TblOrder>(entity =>
            {
                entity.ToTable("tblOrder");

                entity.HasIndex(e => e.UserId, "IX_tblOrder_userId");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.DeletedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("deletedDate");

                entity.Property(e => e.StatusOfOder).HasColumnName("statusOfOder");

                entity.Property(e => e.Total)
                    .HasColumnType("decimal(10, 2)")
                    .HasColumnName("total");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");

                entity.Property(e => e.UserId).HasColumnName("userId");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.TblOrders)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("FK_tblOrder_tblUser");
            });

            modelBuilder.Entity<TblUser>(entity =>
            {
                entity.ToTable("tblUser");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Address)
                    .HasColumnType("text")
                    .HasColumnName("address");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.Email)
                    .HasMaxLength(100)
                    .IsUnicode(false)
                    .HasColumnName("email");

                entity.Property(e => e.Password)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("password");

                entity.Property(e => e.Phone)
                    .HasMaxLength(20)
                    .IsUnicode(false)
                    .HasColumnName("phone");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");

                entity.Property(e => e.Username)
                    .HasMaxLength(50)
                    .IsUnicode(false)
                    .HasColumnName("username");
            });

            modelBuilder.Entity<Topping>(entity =>
            {
                entity.ToTable("Topping");

                entity.Property(e => e.Id)
                    .ValueGeneratedNever()
                    .HasColumnName("id");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("createdDate");

                entity.Property(e => e.Name)
                    .HasMaxLength(100)
                    .IsUnicode(false)
                    .HasColumnName("name");

                entity.Property(e => e.Price)
                    .HasColumnType("decimal(10, 2)")
                    .HasColumnName("price");

                entity.Property(e => e.UpdatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("updatedDate");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
