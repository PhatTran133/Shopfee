using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace BussinessObjects.Migrations
{
    public partial class migration_2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Iced",
                table: "OrderToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Note",
                table: "OrderToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Size",
                table: "OrderToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Sugar",
                table: "OrderToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Variant",
                table: "OrderToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Iced",
                table: "CartToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Note",
                table: "CartToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Size",
                table: "CartToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Sugar",
                table: "CartToppingDrinks",
                type: "text",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Variant",
                table: "CartToppingDrinks",
                type: "text",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Iced",
                table: "OrderToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Note",
                table: "OrderToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Size",
                table: "OrderToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Sugar",
                table: "OrderToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Variant",
                table: "OrderToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Iced",
                table: "CartToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Note",
                table: "CartToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Size",
                table: "CartToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Sugar",
                table: "CartToppingDrinks");

            migrationBuilder.DropColumn(
                name: "Variant",
                table: "CartToppingDrinks");
        }
    }
}
