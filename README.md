# E-Commerce Application

An e-commerce backend aplication built using Java Spring Boot and MySQL providing functionalities for product management, cart and order handling, reviews, and payment processing. This application includes robust logging, error handling, and testing.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Accessing the Application](#accessing-the-application)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Logging and Monitoring](#logging-and-monitoring)
- [Testing](#testing)

## Overview

This e-commerce backend aplication provides an online shopping experience, handling from browsing products to completing purchases. The backend, developed with Spring Boot, supports CRUD operations on products, handles cart and order processing, manages user authentication, allows users to review products, and integrates payment functionalities.

## Features

- **Product Management**: Add, update, delete, and retrieve products.
- **Order Processing**: create orders, and view orders history.
- **User Reviews and Ratings**: Add ratings and feedback on products.
- **Payment Processing**: Integration with payment gateway (razorpay).
- **Logging and Error Handling**: Track actions, debug issues, and capture system errors.
- **Monitoring**: Real-time application monitoring for performance and errors.
- **Testing**: Extensive unit and integration testing with JUnit and Mockito.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Data JPA, Spring Web
- **Database**: MySQL
- **Testing**: JUnit, Mockito
- **Logging**: SLF4J, Logback
- **Monitoring**: Spring Boot Actuator
  
## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17+
- MySQL
- Maven

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/VarshaRani9/E-Commerce-Application.git
   ```
2. Navigate to the project directory
   ```
   cd E-Commerce-Application
   ```
3. Configure the Database
   Update application.properties in src/main/resources with your MySQL database configurations:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   ```

### Accessing the Application
   After running the application, it will be accessible at http://localhost:8080 (by default).

## API Endpoints
```

| Endpoint                                 | Method | Description                              |
|------------------------------------------|--------|------------------------------------------|
| `/products`                              | POST   | Create a new product                     |
| `/products`                              | GET    | Get all products                         |
| `/product/{id}`                          | GET    | Get product by ID                        |
| `/product/{id}`                          | PUT    | Update a product by ID                   |
| `/product/{id}`                          | DELETE | Delete a product by ID                   |
| `/orderitems`                            | POST   | Add order items                          |
| `/orderitems`                            | GET    | Get all order items                      |
| `/orderitems/{id}`                       | GET    | Get order item by ID                     |
| `/users`                                 | POST   | Register a new user                      |
| `/users`                                 | GET    | Get all users                            |
| `/users/{id}`                            | GET    | Get user details by ID                   |
| `/orders`                                | POST   | Create a new order                       |
| `/orders`                                | GET    | Get all orders                           |
| `/orders/{id}`                           | GET    | Get order by ID                          |
| `/reviews`                               | POST   | Add a product review                     |
| `/reviews/product/{productId}`           | GET    | Get reviews for a product                |
| `/payment/createOrder`                   | POST   | Create a payment order                   |
| `/monitoring`                            | POST   | Monitoring endpoint                      |
```

## Project Structure

```plaintext
src
├── main
│   ├── java/com/example/demo
│   │   ├── controller    # REST controllers for API endpoints
│   │   ├── entity        # JPA entities (Product, User, Order, etc.)
│   │   ├── repository    # Repository interfaces for data access
│   │   ├── service       # Business logic layer
│   │   └── config        # Configuration classes
│   └── resources
│       ├── application.properties  # Application configurations
└── test
    └── java/com/example/demo       # Unit and integration tests

```
    
## Logging and Monitoring
Logging: SLF4J and Logback are used for logging.

Monitoring: Real-time performance monitoring is implemented to capture key metrics and error rates using Spring Boot Actuator.

## Testing

Unit and integration tests are implemented using JUnit and Mockito to ensure application reliability.

   
