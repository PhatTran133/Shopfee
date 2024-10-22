using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace BussinessObjects.Migrations
{
    public partial class migration_5 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CartToppingDrinks");

            migrationBuilder.DropTable(
                name: "OrderToppingDrinks");

            migrationBuilder.DropTable(
                name: "DrinkToppings");

            migrationBuilder.DropColumn(
                name: "DeletedDate",
                table: "Carts");

            migrationBuilder.CreateTable(
                name: "CartItems",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    CartId = table.Column<int>(type: "integer", nullable: false),
                    DrinkId = table.Column<int>(type: "integer", nullable: false),
                    Variant = table.Column<string>(type: "text", nullable: true),
                    Size = table.Column<string>(type: "text", nullable: true),
                    Sugar = table.Column<string>(type: "text", nullable: true),
                    Iced = table.Column<string>(type: "text", nullable: true),
                    Note = table.Column<string>(type: "text", nullable: true),
                    Quantity = table.Column<int>(type: "integer", nullable: false),
                    TotalPrice = table.Column<decimal>(type: "numeric", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CartItems", x => x.Id);
                    table.ForeignKey(
                        name: "FK_CartItems_Carts_CartId",
                        column: x => x.CartId,
                        principalTable: "Carts",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_CartItems_Drinks_DrinkId",
                        column: x => x.DrinkId,
                        principalTable: "Drinks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "OrderItems",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    OrderId = table.Column<int>(type: "integer", nullable: false),
                    DrinkId = table.Column<int>(type: "integer", nullable: false),
                    Variant = table.Column<string>(type: "text", nullable: true),
                    Size = table.Column<string>(type: "text", nullable: true),
                    Sugar = table.Column<string>(type: "text", nullable: true),
                    Iced = table.Column<string>(type: "text", nullable: true),
                    Note = table.Column<string>(type: "text", nullable: true),
                    Quantity = table.Column<int>(type: "integer", nullable: false),
                    TotalPrice = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OrderItems", x => x.Id);
                    table.ForeignKey(
                        name: "FK_OrderItems_Drinks_DrinkId",
                        column: x => x.DrinkId,
                        principalTable: "Drinks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_OrderItems_TblOrders_OrderId",
                        column: x => x.OrderId,
                        principalTable: "TblOrders",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "CartItemToppings",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    CartItemId = table.Column<int>(type: "integer", nullable: false),
                    ToppingId = table.Column<int>(type: "integer", nullable: false),
                    Price = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CartItemToppings", x => x.Id);
                    table.ForeignKey(
                        name: "FK_CartItemToppings_CartItems_CartItemId",
                        column: x => x.CartItemId,
                        principalTable: "CartItems",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_CartItemToppings_Toppings_ToppingId",
                        column: x => x.ToppingId,
                        principalTable: "Toppings",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "OrderItemToppings",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    OrderItemId = table.Column<int>(type: "integer", nullable: false),
                    ToppingId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OrderItemToppings", x => x.Id);
                    table.ForeignKey(
                        name: "FK_OrderItemToppings_OrderItems_OrderItemId",
                        column: x => x.OrderItemId,
                        principalTable: "OrderItems",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_OrderItemToppings_Toppings_ToppingId",
                        column: x => x.ToppingId,
                        principalTable: "Toppings",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_CartItems_CartId",
                table: "CartItems",
                column: "CartId");

            migrationBuilder.CreateIndex(
                name: "IX_CartItems_DrinkId",
                table: "CartItems",
                column: "DrinkId");

            migrationBuilder.CreateIndex(
                name: "IX_CartItemToppings_CartItemId",
                table: "CartItemToppings",
                column: "CartItemId");

            migrationBuilder.CreateIndex(
                name: "IX_CartItemToppings_ToppingId",
                table: "CartItemToppings",
                column: "ToppingId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderItems_DrinkId",
                table: "OrderItems",
                column: "DrinkId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderItems_OrderId",
                table: "OrderItems",
                column: "OrderId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderItemToppings_OrderItemId",
                table: "OrderItemToppings",
                column: "OrderItemId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderItemToppings_ToppingId",
                table: "OrderItemToppings",
                column: "ToppingId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CartItemToppings");

            migrationBuilder.DropTable(
                name: "OrderItemToppings");

            migrationBuilder.DropTable(
                name: "CartItems");

            migrationBuilder.DropTable(
                name: "OrderItems");

            migrationBuilder.AddColumn<DateTime>(
                name: "DeletedDate",
                table: "Carts",
                type: "timestamp with time zone",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "DrinkToppings",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    DrinkId = table.Column<int>(type: "integer", nullable: true),
                    ToppingId = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DrinkToppings", x => x.Id);
                    table.ForeignKey(
                        name: "FK_DrinkToppings_Drinks_DrinkId",
                        column: x => x.DrinkId,
                        principalTable: "Drinks",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_DrinkToppings_Toppings_ToppingId",
                        column: x => x.ToppingId,
                        principalTable: "Toppings",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "CartToppingDrinks",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    CartId = table.Column<int>(type: "integer", nullable: true),
                    ToppingDrinkId = table.Column<int>(type: "integer", nullable: true),
                    Iced = table.Column<string>(type: "text", nullable: true),
                    Note = table.Column<string>(type: "text", nullable: true),
                    Quantity = table.Column<int>(type: "integer", nullable: true),
                    Size = table.Column<string>(type: "text", nullable: true),
                    Sugar = table.Column<string>(type: "text", nullable: true),
                    Total = table.Column<int>(type: "integer", nullable: false),
                    Variant = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CartToppingDrinks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_CartToppingDrinks_Carts_CartId",
                        column: x => x.CartId,
                        principalTable: "Carts",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_CartToppingDrinks_DrinkToppings_ToppingDrinkId",
                        column: x => x.ToppingDrinkId,
                        principalTable: "DrinkToppings",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "OrderToppingDrinks",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    OrderId = table.Column<int>(type: "integer", nullable: true),
                    ToppingDrinkId = table.Column<int>(type: "integer", nullable: true),
                    Iced = table.Column<string>(type: "text", nullable: true),
                    Note = table.Column<string>(type: "text", nullable: true),
                    Quantity = table.Column<int>(type: "integer", nullable: true),
                    Size = table.Column<string>(type: "text", nullable: true),
                    Sugar = table.Column<string>(type: "text", nullable: true),
                    Variant = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OrderToppingDrinks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_OrderToppingDrinks_DrinkToppings_ToppingDrinkId",
                        column: x => x.ToppingDrinkId,
                        principalTable: "DrinkToppings",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_OrderToppingDrinks_TblOrders_OrderId",
                        column: x => x.OrderId,
                        principalTable: "TblOrders",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_CartToppingDrinks_CartId",
                table: "CartToppingDrinks",
                column: "CartId");

            migrationBuilder.CreateIndex(
                name: "IX_CartToppingDrinks_ToppingDrinkId",
                table: "CartToppingDrinks",
                column: "ToppingDrinkId");

            migrationBuilder.CreateIndex(
                name: "IX_DrinkToppings_DrinkId",
                table: "DrinkToppings",
                column: "DrinkId");

            migrationBuilder.CreateIndex(
                name: "IX_DrinkToppings_ToppingId",
                table: "DrinkToppings",
                column: "ToppingId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderToppingDrinks_OrderId",
                table: "OrderToppingDrinks",
                column: "OrderId");

            migrationBuilder.CreateIndex(
                name: "IX_OrderToppingDrinks_ToppingDrinkId",
                table: "OrderToppingDrinks",
                column: "ToppingDrinkId");
        }
    }
}
