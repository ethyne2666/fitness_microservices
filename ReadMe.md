# 🏋️ AI-Powered Fitness Microservices

A full-stack AI-powered fitness application built using **Spring Boot Microservices, Spring Cloud, React, MongoDB, PostgreSQL, RabbitMQ, Keycloak, and Google Gemini AI**.

The project follows a microservices architecture with centralized configuration, service discovery, API Gateway, authentication, asynchronous messaging, and AI-powered fitness recommendations.

---

## 🏗️ Architecture

```text
                         ┌──────────────────────┐
                         │    React Frontend    │
                         │      :5173           │
                         └──────────┬───────────┘
                                    │
                              OAuth2 / PKCE
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │      Keycloak        │
                         │       :8181          │
                         └──────────┬───────────┘
                                    │ JWT
                                    ▼
                         ┌──────────────────────┐
                         │     API Gateway      │
                         │       :8080          │
                         └──────────┬───────────┘
                                    │
                  ┌─────────────────┼─────────────────┐
                  │                 │                 │
                  ▼                 ▼                 ▼
          ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
          │ User Service │  │   Activity   │  │  AI Service  │
          │    :8081     │  │   Service    │  │    :8083     │
          │              │  │    :8082     │  │              │
          └──────┬───────┘  └──────┬───────┘  └──────┬───────┘
                 │                 │                 │
                 ▼                 ▼                 ▼
            PostgreSQL          MongoDB            MongoDB
          fitness_user_db    fitnessActivity   fitnessrecommendation
                                    │
                                    ▼
                              ┌────────────┐
                              │  RabbitMQ  │
                              │    :5672   │
                              └─────┬──────┘
                                    │
                                    ▼
                               AI Service
                                    │
                                    ▼
                              Google Gemini
                                    │
                                    ▼
                              Recommendation


        ┌──────────────────────────────────────┐
        │             Eureka Server             │
        │                 :8761                 │
        │          Service Discovery            │
        └──────────────────────────────────────┘

        ┌──────────────────────────────────────┐
        │             Config Server             │
        │                 :9000                 │
        │       Centralized Configuration       │
        └──────────────────────────────────────┘

```
# services

| Service          |  Port | Technology            | Purpose                   |
| ---------------- | ----: | --------------------- | ------------------------- |
| Eureka Server    |  8761 | Spring Cloud Eureka   | Service Discovery         |
| Config Server    |  9000 | Spring Cloud Config   | Centralized Configuration |
| API Gateway      |  8080 | Spring Cloud Gateway  | API Routing & Security    |
| User Service     |  8081 | Spring Boot + JPA     | User Management           |
| Activity Service |  8082 | Spring Boot + MongoDB | Fitness Activities        |
| AI Service       |  8083 | Spring Boot + MongoDB | AI Recommendations        |
| React Frontend   |  5173 | React + Vite          | User Interface            |
| Keycloak         |  8181 | Keycloak              | Authentication            |
| RabbitMQ         |  5672 | RabbitMQ              | Async Messaging           |
| PostgreSQL       |  5432 | PostgreSQL            | User Database             |
| MongoDB          | 27017 | MongoDB               | Activity & AI Database    |



# 🔧 Technologies Used

| Backend | Frontend | Database | AI |
|---|---|---|---|
| Java 25 | React | PostgreSQL | Google Gemini |
| Spring Boot | Vite | MongoDB | Gemini API |
| Spring Cloud | React Router |  | AI Recommendations |
| Spring Cloud Gateway | Redux Toolkit |  |  |
| Spring Cloud Config | Material UI |  |  |
| Spring Cloud Netflix Eureka | Axios |  |  |
| Spring Security | OAuth2 PKCE |  |  |
| Spring WebFlux |  |  |  |
| Spring Data JPA |  |  |  |
| Spring Data MongoDB |  |  |  |
| Spring AMQP |  |  |  |
| WebClient |  |  |  |
| Lombok |  |  |  |
| RabbitMQ |  |  |  |
| Keycloak |  |  |  |
| OAuth2 / OpenID Connect |  |  |  |
| JWT |  |  |  |
| Docker |  |  |  |

---

# 📁 Project Structure

```text
fitness_microservices/
│
├── activityService/
│
├── aiService/
│
├── configServer/
│   └── src/main/resources/
│       ├── application.yml
│       └── config/
│           ├── activity-service.yml
│           ├── ai-service.yml
│           ├── api-gateway.yml
│           └── user-service.yml
│
├── eureka/
│
├── gateway/
│
├── userService/
│
├── fitness-app-frontend/
│
└── ReadMe.md

```

# ⚙️ Configuration Server

The project uses **Spring Cloud Config Server** to provide centralized configuration for all microservices.

Instead of maintaining separate configuration values inside every service, the configuration is managed centrally by the Config Server.

### Config Server

```text
Port: 9000
URL: http://localhost:9000

```

### Configuration files are maintained inside
configServer/
└── src/
└── main/
└── resources/
├── application.yml
└── config/
├── activity-service.yml
├── ai-service.yml
├── api-gateway.yml
└── user-service.yml

# Configuration flow
                    ┌─────────────────────┐
                    │    Config Server    │
                    │       :9000         │
                    └──────────┬──────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              ▼                ▼                ▼
       User Service     Activity Service    AI Service
          :8081              :8082             :8083
              │                │                │
              └────────────────┼────────────────┘
                               ▼
                    Centralized Configuration


# API Gateway

```
Port: 8080
URL: http://localhost:8080
```

                         API Gateway
                            :8080
                              │
             ┌────────────────┼────────────────┐
             │                │                │
             ▼                ▼                ▼
       /api/users/**   /api/activities/**  /api/recommendations/**
             │                │                │
             ▼                ▼                ▼
       USER-SERVICE     ACTIVITY-SERVICE    AI-SERVICE
          :8081              :8082             :8083


# RabbitMQ

Host: localhost
AMQP Port: 5672
Management Port: 15672
Username: guest
Password: guest


RabbitMQ Configuration
Exchange:
fitness.exchange

Queue:
activity.queue

Routing Key:
activity.tracking


# 🔐 Keycloak Authentication

The application uses **Keycloak** for authentication and authorization.

Keycloak provides the identity and access management layer for the application.

### Keycloak

```text
Port: 8181
URL: http://localhost:8181
```

Configured Realm:

```text
fitness-oauth2
```

Configured Client:

```text
oauth2-pkce-client
```

The frontend uses:

```text
OAuth2 Authorization Code Flow
+
PKCE
```

The API Gateway acts as an OAuth2 Resource Server and validates JWT access tokens.

---

# 🔑 Authentication Flow

```text
                 👤 USER
                    │
                    ▼
             React Frontend
                 :5173
                    │
                    │ Login
                    ▼
               Keycloak
                 :8181
                    │
                    │ Authorization Code
                    │ + PKCE
                    ▼
             React Frontend
                    │
                    │ Access Token
                    ▼
              API Gateway
                 :8080
                    │
                    │ JWT Validation
                    ▼
             Spring Security
                    │
                    ▼
              Microservice
```

The access token is sent with API requests:

```http
Authorization: Bearer <JWT>
```

The Gateway validates the JWT before allowing access to protected routes.

---

# 🔒 Security Architecture

```text
┌──────────────────┐
│ React Frontend   │
└────────┬─────────┘
         │
         │ OAuth2 + PKCE
         ▼
┌──────────────────┐
│    Keycloak      │
│      :8181       │
└────────┬─────────┘
         │
         │ JWT
         ▼
┌──────────────────┐
│   API Gateway    │
│      :8080       │
│                  │
│ Spring Security  │
│ JWT Validation   │
└────────┬─────────┘
         │
         ▼
     Microservices
```

Security technologies:

- Keycloak
- OAuth2
- OpenID Connect
- PKCE
- JWT
- Spring Security
- OAuth2 Resource Server

---

# 🐳 Docker

Docker is used to run infrastructure components required by the application.

The project currently uses Docker images/containers for:

- RabbitMQ
- Keycloak

---

# 🐇 RabbitMQ Docker Image

Pull the RabbitMQ image:

```bash
docker pull rabbitmq:4-management
```

Create and run the RabbitMQ container:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management
```

Check running containers:

```bash
docker ps
```

RabbitMQ Management Dashboard:

```text
http://localhost:15672
```

Login:

```text
Username: guest
Password: guest
```

View RabbitMQ logs:

```bash
docker logs rabbitmq
```

Follow RabbitMQ logs:

```bash
docker logs -f rabbitmq
```

Stop RabbitMQ:

```bash
docker stop rabbitmq
```

Start RabbitMQ:

```bash
docker start rabbitmq
```

Restart RabbitMQ:

```bash
docker restart rabbitmq
```

Remove RabbitMQ container:

```bash
docker rm rabbitmq
```

---

# 🔑 Keycloak Docker Image

Pull the Keycloak image:

```bash
docker pull quay.io/keycloak/keycloak:latest
```

Create and run Keycloak:

```bash
docker run -d --name keycloak -p 8181:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

Check running containers:

```bash
docker ps
```

View Keycloak logs:

```bash
docker logs keycloak
```

Follow Keycloak logs:

```bash
docker logs -f keycloak
```

Stop Keycloak:

```bash
docker stop keycloak
```

Start Keycloak:

```bash
docker start keycloak
```

Restart Keycloak:

```bash
docker restart keycloak
```

Remove Keycloak container:

```bash
docker rm keycloak
```

Keycloak URL:

```text
http://localhost:8181
```

---

# 🐳 Docker Useful Commands

List running containers:

```bash
docker ps
```

List all containers:

```bash
docker ps -a
```

List Docker images:

```bash
docker images
```

Pull an image:

```bash
docker pull <image-name>
```

Start a container:

```bash
docker start <container-name>
```

Stop a container:

```bash
docker stop <container-name>
```

Restart a container:

```bash
docker restart <container-name>
```

Remove a container:

```bash
docker rm <container-name>
```

Remove an image:

```bash
docker rmi <image-name>
```

View container logs:

```bash
docker logs <container-name>
```

Follow container logs:

```bash
docker logs -f <container-name>
```

---

# 🗄️ PostgreSQL Setup

The User Service uses PostgreSQL as its relational database.

PostgreSQL should be running on:

```text
localhost:5432
```

Create the database:

```sql
CREATE DATABASE fitness_user_db;
```

Expected configuration:

```text
Database: fitness_user_db
Username: postgres
Password: root
Port: 5432
```

JDBC connection:

```text
jdbc:postgresql://localhost:5432/fitness_user_db
```

The PostgreSQL configuration is maintained through the Config Server:

```text
configServer/src/main/resources/config/user-service.yml
```

---

# 🍃 MongoDB Setup

MongoDB should be running on:

```text
localhost:27017
```

The project uses MongoDB for two different services.

### Activity Service

```text
Database:
fitnessActivity
```

Connection:

```text
mongodb://localhost:27017/fitnessActivity
```

### AI Service

```text
Database:
fitnessrecommendation
```

Connection:

```text
mongodb://localhost:27017/fitnessrecommendation
```

### MongoDB Architecture

```text
                 MongoDB
                    │
          ┌─────────┴─────────┐
          │                   │
          ▼                   ▼
   fitnessActivity    fitnessrecommendation
          │                   │
          ▼                   ▼
 Activity Service         AI Service
```

---

# 🧠 Google Gemini AI

The AI Service integrates with **Google Gemini** to generate personalized fitness recommendations.

The Gemini configuration is maintained in:

```text
configServer/src/main/resources/config/ai-service.yml
```

Example configuration:

```yaml
gemini:
  api:
    url: <GEMINI_API_URL>
    key: <GEMINI_API_KEY>
```

The actual Gemini API key should **never be committed to GitHub**.

For production deployments, environment variables or a secret-management system should be used.

---

# ▶️ Running the Project

Before starting the Spring Boot services, make sure the required infrastructure is running.

---

## 1️⃣ Start PostgreSQL

Make sure PostgreSQL is running:

```text
localhost:5432
```

Verify that the database exists:

```text
fitness_user_db
```

---

## 2️⃣ Start MongoDB

Make sure MongoDB is running:

```text
localhost:27017
```

---

## 3️⃣ Start RabbitMQ

If the RabbitMQ container already exists:

```bash
docker start rabbitmq
```

If the container does not exist:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management
```

Verify:

```bash
docker ps
```

---

## 4️⃣ Start Keycloak

If the Keycloak container already exists:

```bash
docker start keycloak
```

If the container does not exist:

```bash
docker run -d --name keycloak -p 8181:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

Verify:

```bash
docker ps
```

---

# 5️⃣ Start Config Server

Open a new terminal.

```bash
cd configServer
```

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Config Server:

```text
http://localhost:9000
```

---

# 6️⃣ Start Eureka Server

Open another terminal.

```bash
cd eureka
```

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Eureka Dashboard:

```text
http://localhost:8761
```

---

# 7️⃣ Start User Service

Open another terminal.

```bash
cd userService
```

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

User Service:

```text
http://localhost:8081
```

---

# 8️⃣ Start Activity Service

Open another terminal.

```bash
cd activityService
```

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Activity Service:

```text
http://localhost:8082
```

---

# 9️⃣ Start AI Service

Open another terminal.

```bash
cd aiService
```

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

AI Service:

```text
http://localhost:8083
```

---

# 🔟 Start API Gateway

Open another terminal.

```bash
cd gateway
```

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

API Gateway:

```text
http://localhost:8080
```

---

# 🌐 Start Frontend

Navigate to the frontend directory:

```bash
cd fitness-app-frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Frontend:

```text
http://localhost:5173
```

Build the frontend:

```bash
npm run build
```

Preview the production build:

```bash
npm run preview
```

Run ESLint:

```bash
npm run lint
```

---

# 📋 Recommended Startup Order

Start the complete system in the following order:

```text
                    INFRASTRUCTURE
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
     PostgreSQL       MongoDB        RabbitMQ
          │              │              │
          └──────────────┼──────────────┘
                         ▼
                      Keycloak
                       :8181
                         │
                         ▼
                 Config Server
                      :9000
                         │
                         ▼
                  Eureka Server
                      :8761
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
     User Service   Activity Service   AI Service
       :8081            :8082           :8083
          │              │              │
          └──────────────┼──────────────┘
                         ▼
                    API Gateway
                       :8080
                         │
                         ▼
                  React Frontend
                       :5173
```

---

# 🧪 API Testing

The frontend should normally communicate with the backend through the API Gateway.

### Backend Base URL

```text
http://localhost:8080
```

---

## 👤 User APIs

Register a user:

```http
POST http://localhost:8080/api/users/register
```

Get a user:

```http
GET http://localhost:8080/api/users/{userId}
```

Validate a user:

```http
GET http://localhost:8080/api/users/{userId}/validate
```

---

## 🏃 Activity APIs

Create an activity:

```http
POST http://localhost:8080/api/activities
```

Get activities:

```http
GET http://localhost:8080/api/activities
```

Get a specific activity:

```http
GET http://localhost:8080/api/activities/{activityId}
```

Authenticated requests require:

```http
Authorization: Bearer <JWT>
```

---

## 🤖 Recommendation APIs

Get recommendations for a user:

```http
GET http://localhost:8080/api/recommendations/user/{userId}
```

Get a recommendation for an activity:

```http
GET http://localhost:8080/api/recommendations/activity/{activityId}
```

---

# 🔄 Complete System Architecture

```text
                                  👤 USER
                                    │
                                    ▼
                         ┌────────────────────┐
                         │   React Frontend   │
                         │       :5173        │
                         └─────────┬──────────┘
                                   │
                                   │ OAuth2 + PKCE
                                   ▼
                         ┌────────────────────┐
                         │      Keycloak      │
                         │       :8181        │
                         └─────────┬──────────┘
                                   │
                                   │ JWT
                                   ▼
                         ┌────────────────────┐
                         │    API Gateway     │
                         │       :8080        │
                         └─────────┬──────────┘
                                   │
                  ┌────────────────┼────────────────┐
                  │                │                │
                  ▼                ▼                ▼
          ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
          │ User Service │ │   Activity   │ │  AI Service  │
          │    :8081     │ │   Service    │ │    :8083     │
          │              │ │    :8082     │ │              │
          └──────┬───────┘ └──────┬───────┘ └──────┬───────┘
                 │                │                │
                 ▼                ▼                ▼
          ┌──────────────┐ ┌──────────────┐ ┌──────────────────────┐
          │ PostgreSQL   │ │   MongoDB    │ │       MongoDB        │
          │              │ │              │ │                      │
          │fitness_user_ │ │fitnessActivity│ │fitnessrecommendation│
          │     db       │ │              │ │                      │
          └──────────────┘ └──────┬───────┘ └──────────────────────┘
                                  │
                                  │ Activity Event
                                  ▼
                           ┌──────────────┐
                           │   RabbitMQ   │
                           │    :5672     │
                           └──────┬───────┘
                                  │
                                  ▼
                           ┌──────────────┐
                           │  AI Service  │
                           └──────┬───────┘
                                  │
                                  │ API Request
                                  ▼
                           ┌──────────────┐
                           │Google Gemini │
                           └──────┬───────┘
                                  │
                                  │ AI Recommendation
                                  ▼
                           ┌──────────────────────┐
                           │       MongoDB        │
                           │   Recommendation     │
                           └──────────────────────┘


       ┌─────────────────────────────────────────┐
       │             SERVICE DISCOVERY           │
       │                                         │
       │             Eureka Server               │
       │                 :8761                   │
       │                                         │
       │  User ────────┐                         │
       │  Activity ────┼──► Eureka               │
       │  AI ──────────┤                         │
       │  Gateway ─────┘                         │
       └─────────────────────────────────────────┘


       ┌─────────────────────────────────────────┐
       │          CENTRALIZED CONFIG             │
       │                                         │
       │            Config Server                │
       │                :9000                    │
       │                                         │
       │  activity-service.yml                   │
       │  ai-service.yml                         │
       │  api-gateway.yml                        │
       │  user-service.yml                       │
       └─────────────────────────────────────────┘
```

---

# 🔗 Service Communication

## Synchronous Communication

The following communication happens synchronously:

```text
API Gateway
     │
     ├──────────► User Service
     │
     ├──────────► Activity Service
     │
     └──────────► AI Service
```

Activity Service can also communicate with User Service when user validation is required.

```text
Activity Service
       │
       │ HTTP / WebClient
       ▼
User Service
```

AI Service communicates with Google Gemini through an API request.

```text
AI Service
     │
     │ HTTP
     ▼
Google Gemini API
```

---

# 🐇 Asynchronous Communication

Activity processing and AI processing use RabbitMQ for asynchronous communication.

```text
Activity Service
       │
       │ Publish Event
       ▼
   RabbitMQ
       │
       │ Consume Event
       ▼
   AI Service
       │
       ▼
 Google Gemini
       │
       ▼
AI Recommendation
```

This allows the activity creation request to remain independent from potentially slower AI processing.

---

# 📊 Database Architecture

```text
                         DATABASE LAYER
                              │
             ┌────────────────┼────────────────┐
             │                │                │
             ▼                ▼                ▼
       PostgreSQL          MongoDB          MongoDB
             │                │                │
             ▼                ▼                ▼
       User Service     Activity Service    AI Service
             │                │                │
             ▼                ▼                ▼
     fitness_user_db    fitnessActivity   fitnessrecommendation
```

Each service is responsible for its own data.

```text
User Service
     ↓
PostgreSQL

Activity Service
     ↓
MongoDB

AI Service
     ↓
MongoDB
```

---

# 🧱 Build Backend Services

Each Spring Boot service can be built using Maven Wrapper.

### Windows

```bash
.\mvnw.cmd clean package
```

Skip tests:

```bash
.\mvnw.cmd clean package -DskipTests
```

Run the application:

```bash
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw clean package
```

Run:

```bash
./mvnw spring-boot:run
```

---

# 📦 Run JAR Files

After building a service:

```bash
java -jar target/<application-name>.jar
```

Example:

```bash
cd userService
java -jar target/userService-0.0.1-SNAPSHOT.jar
```

---

# 🐛 Troubleshooting

## ❌ Config Server Connection Error

Make sure Config Server is running first:

```text
http://localhost:9000
```

The Config Server should be started before the Config Client microservices.

Check:

```text
configServer/src/main/resources/config/
```

for the required service configuration files.

---

## ❌ Eureka Services Not Appearing

Open:

```text
http://localhost:8761
```

Check whether the following services are registered:

```text
ACTIVITY-SERVICE
AI-SERVICE
API-GATEWAY
USER-SERVICE
```

Make sure Eureka Server is started before the microservices.

---

## ❌ PostgreSQL Connection Error

Verify PostgreSQL:

```text
Host: localhost
Port: 5432
Database: fitness_user_db
```

Check the credentials configured in:

```text
configServer/src/main/resources/config/user-service.yml
```

---

## ❌ MongoDB Connection Error

Verify MongoDB:

```text
localhost:27017
```

Activity Service:

```text
fitnessActivity
```

AI Service:

```text
fitnessrecommendation
```

---

## ❌ RabbitMQ Connection Error

Verify RabbitMQ is running:

```bash
docker ps
```

Check:

```text
AMQP:
localhost:5672
```

Management UI:

```text
http://localhost:15672
```

Check the configured:

```text
Exchange:
fitness.exchange

Queue:
activity.queue

Routing Key:
activity.tracking
```

---

## ❌ Gateway 401 Unauthorized

A `401 Unauthorized` response usually means that the request is not properly authenticated.

Check:

```text
1. Keycloak is running
2. Correct realm is configured
3. Correct client is configured
4. Frontend received an access token
5. Authorization header is present
6. JWT is valid
```

The request should contain:

```http
Authorization: Bearer <JWT>
```

Keycloak:

```text
http://localhost:8181
```

Gateway:

```text
http://localhost:8080
```

---

## ❌ AI Recommendation Not Generated

Check the following components:

```text
1. RabbitMQ
2. Activity Service
3. AI Service
4. Gemini API URL
5. Gemini API Key
6. MongoDB
```

Check RabbitMQ:

```text
Exchange:
fitness.exchange

Queue:
activity.queue

Routing Key:
activity.tracking
```

Check AI Service logs for Gemini API errors.

---

# 🎯 Key Features

- 🔐 **Keycloak Authentication**
- 🔑 **OAuth2 + PKCE**
- 🪪 **JWT-based Security**
- 🚪 **Spring Cloud API Gateway**
- 🔍 **Eureka Service Discovery**
- ⚙️ **Centralized Configuration**
- 👤 **User Management**
- 🏃 **Fitness Activity Tracking**
- 🐇 **RabbitMQ Asynchronous Messaging**
- 🤖 **AI-powered Fitness Recommendations**
- 🧠 **Google Gemini Integration**
- 🗄️ **PostgreSQL User Database**
- 🍃 **MongoDB Activity Database**
- 📊 **MongoDB Recommendation Database**
- ⚛️ **React Frontend**
- 📦 **Dockerized Infrastructure**
- 🔄 **Microservice-to-Microservice Communication**
- ⚡ **Reactive API Gateway using WebFlux**

---

# 📚 Concepts Demonstrated

This project demonstrates practical implementation of:

### Backend

- Java
- Spring Boot
- Spring Cloud
- Spring Cloud Config
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Spring Security
- Spring WebFlux
- Spring Data JPA
- Spring Data MongoDB
- Spring AMQP
- WebClient
- Lombok
- REST APIs
- Reactive Programming
- Microservices Architecture

### Security

- Keycloak
- OAuth2
- OpenID Connect
- Authorization Code Flow
- PKCE
- JWT
- OAuth2 Resource Server

### Messaging

- RabbitMQ
- Exchanges
- Queues
- Routing Keys
- Asynchronous Communication

### Databases

- PostgreSQL
- MongoDB
- JPA / Hibernate

### Frontend

- React
- Vite
- React Router
- Redux Toolkit
- Material UI
- Axios
- OAuth2 PKCE

### AI

- Google Gemini API
- AI-powered Fitness Recommendations
- AI Service
- Activity-based Recommendation Generation

### DevOps / Infrastructure

- Docker
- Docker Images
- Docker Containers
- Eureka
- Config Server

---

# 🌟 Project Highlights

```text
┌────────────────────────────────────────────────────┐
│             🚀 FITNESS MICROSERVICES               │
├────────────────────────────────────────────────────┤
│                                                    │
│  ⚛️ React Frontend                                 │
│          ↓                                         │
│  🔐 Keycloak + OAuth2 + PKCE                       │
│          ↓                                         │
│  🚪 API Gateway                                    │
│          ↓                                         │
│  🔍 Eureka Service Discovery                       │
│          ↓                                         │
│  ┌────────────┬──────────────┬───────────────┐    │
│  │ User       │ Activity     │ AI            │    │
│  │ Service    │ Service      │ Service       │    │
│  │ :8081      │ :8082        │ :8083         │    │
│  └─────┬──────┴──────┬───────┴──────┬────────┘    │
│        │              │              │             │
│        ▼              ▼              ▼             │
│   PostgreSQL       MongoDB        MongoDB          │
│                                   │                │
│                                   ▼                │
│                              🤖 Gemini             │
│                                                    │
│  🐇 RabbitMQ provides asynchronous communication   │
│                                                    │
│  ⚙️ Config Server provides centralized config      │
│                                                    │
│  🐳 Docker runs infrastructure services            │
│                                                    │
└────────────────────────────────────────────────────┘
```

---

# 📌 Service Port Summary

| Service | Port | URL |
|---|---:|---|
| React Frontend | 5173 | http://localhost:5173 |
| API Gateway | 8080 | http://localhost:8080 |
| User Service | 8081 | http://localhost:8081 |
| Activity Service | 8082 | http://localhost:8082 |
| AI Service | 8083 | http://localhost:8083 |
| Eureka Server | 8761 | http://localhost:8761 |
| Config Server | 9000 | http://localhost:9000 |
| Keycloak | 8181 | http://localhost:8181 |
| RabbitMQ | 5672 | localhost:5672 |
| RabbitMQ Management | 15672 | http://localhost:15672 |
| PostgreSQL | 5432 | localhost:5432 |
| MongoDB | 27017 | localhost:27017 |

---

# 👨‍💻 Author

## Charan Kumar

GitHub:

https://github.com/ethyne2666

Repository:

https://github.com/ethyne2666/fitness_microservices

---

# ⭐ Support

If you found this project useful for learning **Spring Boot Microservices, Spring Cloud, Keycloak, RabbitMQ, MongoDB, PostgreSQL, React, and AI integration**, consider giving the repository a ⭐.
