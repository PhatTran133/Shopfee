using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace BussinessObjects.Migrations
{
    public partial class migration_3 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_CartToppingDrinks_Carts_CartId",
                table: "CartToppingDrinks");

            migrationBuilder.AddForeignKey(
                name: "FK_CartToppingDrinks_Carts_CartId",
                table: "CartToppingDrinks",
                column: "CartId",
                principalTable: "Carts",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_CartToppingDrinks_Carts_CartId",
                table: "CartToppingDrinks");

            migrationBuilder.AddForeignKey(
                name: "FK_CartToppingDrinks_Carts_CartId",
                table: "CartToppingDrinks",
                column: "CartId",
                principalTable: "Carts",
                principalColumn: "Id");
        }
    }
}
