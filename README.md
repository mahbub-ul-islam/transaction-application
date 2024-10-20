# Transaction Application

## Overview

The Transaction Application is a microservices-based application built using Spring Boot and Angular. It provides functionalities for transaction management, including user authentication, transaction creation, and retrieval of transaction data.

## Features

- User authentication with JWT
- Create and manage transactions
- RESTful API for frontend integration
- Service discovery with Eureka
- API Gateway for routing requests
- PostgreSQL for data storage
- Dockerized application with Docker Compose

## Architecture

The application consists of the following modules:

- **discovery-service**: Eureka server for service discovery
- **api-gateway**: Gateway service for routing and authentication
- **transaction-service**: Microservice for managing transactions
- **auth-service**: Service for user authentication
- **PostgreSQL**: Database for storing transaction data
- **pgAdmin**: Web-based database management tool

## Technologies Used

- Spring Boot 3
- PostgreSQL
- Docker
- JUnit 5 & Mockito for testing

## Getting Started

### Prerequisites

- Docker and Docker Compose installed on your machine

### Installation

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd transaction-application

2. Build and run the application using Docker Compose to start PostgreSQL and pgAdmin:

    ```bash
    docker-compose up --build

3. Access pgAdmin 

   Link: http://localhost:5050.

       Username: admin@example.com
       Password: admin

4. Register to the database.
    
        Name: transaction-application
        Host name/address: 172.20.0.2
        Port: 5432
        Username: admin
        Password: admin
    Check the Postgres DB address on docker if necessary.
    

5. Create a new database for auth-service.

       authentication

6. Start the Discovery Service and check.

   Link: http://localhost:8761


7. Start the API Gateway.


8. Start the Auth Service.


9. Start the Transaction Service.

    If the batch tables are not created, run the clean command.

Finally, run the Transaction UI at http://localhost:4200.