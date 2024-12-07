FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443
EXPOSE 5010

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["PRM392_CafeOnline_BE_API/PRM392_CafeOnline_BE_API.csproj", "PRM392_CafeOnline_BE_API/"]
COPY ["BussinessObjects/BussinessObjects.csproj", "BussinessObjects/"]
COPY ["DataAccess/DataAccess.csproj", "DataAccess/"]
COPY ["Repositories/Repositories.csproj", "Repositories/"]

RUN dotnet restore "PRM392_CafeOnline_BE_API/PRM392_CafeOnline_BE_API.csproj"
COPY . .
WORKDIR "/src/PRM392_CafeOnline_BE_API"
RUN dotnet build "PRM392_CafeOnline_BE_API.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "PRM392_CafeOnline_BE_API.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
RUN mkdir -p /app/uploads
VOLUME /app/uploads
COPY --from=publish /app/publish .

ENTRYPOINT ["dotnet", "PRM392_CafeOnline_BE_API.dll"]