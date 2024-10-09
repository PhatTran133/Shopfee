using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace BussinessObjects.Migrations
{
    public partial class InitialCreate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Category",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false),
                    name = table.Column<string>(type: "varchar(100)", unicode: false, maxLength: 100, nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Category", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "tblUser",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    username = table.Column<string>(type: "varchar(50)", unicode: false, maxLength: 50, nullable: true),
                    email = table.Column<string>(type: "varchar(100)", unicode: false, maxLength: 100, nullable: true),
                    password = table.Column<string>(type: "varchar(255)", unicode: false, maxLength: 255, nullable: true),
                    phone = table.Column<string>(type: "varchar(20)", unicode: false, maxLength: 20, nullable: true),
                    address = table.Column<string>(type: "text", nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    Otp = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    OtpExpiration = table.Column<DateTime>(type: "datetime2", nullable: false),
                    EmailVerified = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblUser", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "Topping",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false),
                    name = table.Column<string>(type: "varchar(100)", unicode: false, maxLength: 100, nullable: true),
                    price = table.Column<decimal>(type: "decimal(10,2)", nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Topping", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "Drink",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    categoryId = table.Column<int>(type: "int", nullable: true),
                    name = table.Column<string>(type: "varchar(100)", unicode: false, maxLength: 100, nullable: true),
                    description = table.Column<string>(type: "text", nullable: true),
                    price = table.Column<decimal>(type: "decimal(10,2)", nullable: true),
                    size = table.Column<string>(type: "varchar(20)", unicode: false, maxLength: 20, nullable: true),
                    image = table.Column<string>(type: "varchar(255)", unicode: false, maxLength: 255, nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    isDeleted = table.Column<bool>(type: "bit", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Drink", x => x.id);
                    table.ForeignKey(
                        name: "FK__Drink__categoryI__3A81B327",
                        column: x => x.categoryId,
                        principalTable: "Category",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "Cart",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    userId = table.Column<int>(type: "int", nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    deletedDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    totalPrice = table.Column<decimal>(type: "decimal(10,2)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Cart", x => x.id);
                    table.ForeignKey(
                        name: "FK_Cart_tblUser",
                        column: x => x.userId,
                        principalTable: "tblUser",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "tblNotification",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    userId = table.Column<int>(type: "int", nullable: true),
                    content = table.Column<string>(type: "varchar(100)", unicode: false, maxLength: 100, nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    deletedDate = table.Column<DateTime>(type: "datetime", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblNotification", x => x.id);
                    table.ForeignKey(
                        name: "FK_tblNotification_tblUser",
                        column: x => x.userId,
                        principalTable: "tblUser",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "tblOrder",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    userId = table.Column<int>(type: "int", nullable: true),
                    total = table.Column<decimal>(type: "decimal(10,2)", nullable: true),
                    statusOfOder = table.Column<bool>(type: "bit", nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    deletedDate = table.Column<DateTime>(type: "datetime", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblOrder", x => x.id);
                    table.ForeignKey(
                        name: "FK_tblOrder_tblUser",
                        column: x => x.userId,
                        principalTable: "tblUser",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "DrinkTopping",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    drinkId = table.Column<int>(type: "int", nullable: true),
                    toppingId = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DrinkTopping", x => x.id);
                    table.ForeignKey(
                        name: "FK__DrinkTopp__drink__3B75D760",
                        column: x => x.drinkId,
                        principalTable: "Drink",
                        principalColumn: "id");
                    table.ForeignKey(
                        name: "FK__DrinkTopp__toppi__3C69FB99",
                        column: x => x.toppingId,
                        principalTable: "Topping",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "Payment",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false),
                    orderId = table.Column<int>(type: "int", nullable: true),
                    type = table.Column<string>(type: "varchar(50)", unicode: false, maxLength: 50, nullable: true),
                    detail = table.Column<string>(type: "varchar(100)", unicode: false, maxLength: 100, nullable: true),
                    createdDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    updatedDate = table.Column<DateTime>(type: "datetime", nullable: true),
                    deletedDate = table.Column<DateTime>(type: "datetime", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Payment", x => x.id);
                    table.ForeignKey(
                        name: "FK__Payment__orderId__3F466844",
                        column: x => x.orderId,
                        principalTable: "tblOrder",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "CartToppingDrink",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    cartId = table.Column<int>(type: "int", nullable: true),
                    toppingDrinkId = table.Column<int>(type: "int", nullable: true),
                    quantity = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CartToppingDrink", x => x.id);
                    table.ForeignKey(
                        name: "FK__CartToppi__cartI__38996AB5",
                        column: x => x.cartId,
                        principalTable: "Cart",
                        principalColumn: "id");
                    table.ForeignKey(
                        name: "FK__CartToppi__toppi__398D8EEE",
                        column: x => x.toppingDrinkId,
                        principalTable: "DrinkTopping",
                        principalColumn: "id");
                });

            migrationBuilder.CreateTable(
                name: "OrderToppingDrink",
                columns: table => new
                {
                    id = table.Column<int>(type: "int", nullable: false),
                    quantity = table.Column<int>(type: "int", nullable: true),
                    orderId = table.Column<int>(type: "int", nullable: true),
                    toppingDrinkId = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OrderToppingDrink", x => x.id);
                    table.ForeignKey(
                        name: "FK__OrderTopp__order__3D5E1FD2",
                        column: x => x.orderId,
                        principalTable: "tblOrder",
                        principalColumn: "id");
                    table.ForeignKey(
                        name: "FK__OrderTopp__toppi__3E52440B",
                        column: x => x.toppingDrinkId,
                        principalTable: "DrinkTopping",
                        principalColumn: "id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_Cart_userId",
                table: "Cart",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_CartToppingDrink_cartId",
                table: "CartToppingDrink",
                column: "cartId");

            migrationBuilder.CreateIndex(
                name: "IX_CartToppingDrink_toppingDrinkId",
                table: "CartToppingDrink",
                column: "toppingDrinkId");

            migrationBuilder.CreateIndex(
                name: "IX_Drink_categoryId",
                table: "Drink",
                column: "categoryId");

            migrationBuilder.CreateIndex(
                name: "IX_DrinkTopping_drinkId",
                table: "DrinkTopping",
                column: "drinkId");

            migrationBuilder.CreateIndex(
                name: "IX_DrinkTopping_toppingId",
                table: "DrinkTopping",
                column: "toppingId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderToppingDrink_orderId",
                table: "OrderToppingDrink",
                column: "orderId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderToppingDrink_toppingDrinkId",
                table: "OrderToppingDrink",
                column: "toppingDrinkId");

            migrationBuilder.CreateIndex(
                name: "IX_Payment_orderId",
                table: "Payment",
                column: "orderId");

            migrationBuilder.CreateIndex(
                name: "IX_tblNotification_userId",
                table: "tblNotification",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_tblOrder_userId",
                table: "tblOrder",
                column: "userId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CartToppingDrink");

            migrationBuilder.DropTable(
                name: "OrderToppingDrink");

            migrationBuilder.DropTable(
                name: "Payment");

            migrationBuilder.DropTable(
                name: "tblNotification");

            migrationBuilder.DropTable(
                name: "Cart");

            migrationBuilder.DropTable(
                name: "DrinkTopping");

            migrationBuilder.DropTable(
                name: "tblOrder");

            migrationBuilder.DropTable(
                name: "Drink");

            migrationBuilder.DropTable(
                name: "Topping");

            migrationBuilder.DropTable(
                name: "tblUser");

            migrationBuilder.DropTable(
                name: "Category");
        }
    }
}
