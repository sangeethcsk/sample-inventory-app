# Inventory Service

## Objective
The Inventory Service is a simple application designed to manage and search inventory data. It provides RESTful APIs for searching inventory items based on various parameters.

## Features
- **Search Inventory**: Search inventory items by ID, name, category, subcategory, etc.
- **Pagination Support**: Supports pagination for efficient data retrieval.
- **MongoDB Integration**: Uses MongoDB as the database for storing inventory data.

## Technologies Used
- **Java 21**
- **Spring Boot 4.0.6**
- **MongoDB**
- **Gradle**: Build tool for dependency management and project configuration.
- **Lombok**: Simplifies Java code with annotations.

## Prerequisites
- Java 21 installed
- MongoDB running locally on `localhost:27017`
- Gradle installed

## Configuration
The application configuration is defined in `src/main/resources/application.yaml`:
```yaml
spring:
  application:
    name: inventory
  data:
    mongodb:
      uri: mongodb://localhost:27017/local