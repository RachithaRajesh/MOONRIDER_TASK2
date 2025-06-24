Product Catalog Microservice

A Spring Boot REST API for managing a product catalog, built with Java, Maven, Docker, and MySQL.

Table of Contents

Features

Version Management

Getting Started

Containerization

API Usage

Changelog



Features
RESTful API for product management

Health check endpoint

Product search with filtering and error handling (v2.0+)

Containerized with Docker and Docker Compose

Semantic versioning and changelog

Version Management
Version	Features
v1.0.0	/health, /products (CRUD)
v1.1.0	Adds /products/search endpoint
v2.0.0	Enhanced /products/search with query params and error handling
Each version is tagged in Git (see tags).

All changes are documented in CHANGELOG.md.

Getting Started

1.Clone the Repository

git clone https://github.com/RachithaRajesh/MOONRIDER_TASK2.git
cd MOONRIDER_TASK2
2.Build the Project (Optional)

mvn clean install -DskipTests

3.Containerization
Docker Compose (Recommended for Local Dev)
Build and start the stack:

docker-compose up --build
This will start both the MySQL database and the Spring Boot app.

4.Standalone Docker
Build the image:

docker build -t product-catalog:latest .
Run the container (requires local MySQL):

docker run -d -p 9191:9191 --name catalog \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/productdb \
  -e SPRING_DATASOURCE_USERNAME=user \
  -e SPRING_DATASOURCE_PASSWORD=password \
  product-catalog:latest

5.Health Check
The container exposes a health check at /actuator/health.

You can verify with:

curl http://localhost:9191/actuator/health

API Usage
GET /products: List all products

POST /products: Add a product

GET /products/search?name=...&category=...&minPrice=...&maxPrice=...: Search products (v2.0+)

GET /actuator/health: Health check

Test endpoints using Postman(I used Postman)

6.Changelog

See CHANGELOG.md for version history.