# Spring Boot & Microservices — Development

This repository is a practical Spring Boot backend learning project designed to answer five questions for every topic:

**What is it? → Why do we need it? → How does it work? → How do we implement it? → How do we explain it in an interview?**

## 1. Overall Learning Roadmap

```text
Java / OOP
    ↓
Spring Boot
    ↓
REST APIs
    ↓
DTO + Validation + Exception Handling
    ↓
Spring Data JPA + Hibernate + ORM
    ↓
Pagination + Sorting
    ↓
JPA Performance / N+1
    ↓
JUnit 5 + Mockito
    ↓
SOLID + Design Principles
    ↓
Design Patterns
    ↓
Spring Actuator
    ↓
Microservices
    ↓
Eureka Service Discovery
    ↓
API Gateway
    ↓
OpenFeign
    ↓
Resilience4j Circuit Breaker
    ↓
Fault Tolerance + Observability
```

---

# 2. Development Plan

## Phase 1 — Spring Boot Fundamentals

### Topics
- `@SpringBootApplication`
- IoC and Dependency Injection
- Beans
- `@Component`, `@Service`, `@Repository`, `@RestController`
- Constructor Injection
- `@Configuration`
- Auto-configuration
- Starter dependencies
- `application.properties`
- Profiles

### Practical Example

Build a Product API:

```text
GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}
```

Architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Interview Note

> Spring Boot simplifies Spring development through auto-configuration, starter dependencies, embedded servers, and production-ready features.

**Important:** Prefer constructor injection because dependencies are explicit, testable, and can be final.

---

# 3. REST API Development

### Topics
- GET / POST / PUT / PATCH / DELETE
- Path variables
- Request parameters
- Request body
- ResponseEntity
- HTTP status codes
- REST API design
- Idempotency

### Practical Example

```http
POST /products
```

```json
{
  "name": "Pizza",
  "price": 250
}
```

Response:

```json
{
  "id": 1,
  "name": "Pizza",
  "price": 250
}
```

### Important Status Codes

```text
200 → OK
201 → Created
204 → No Content
400 → Bad Request
401 → Unauthorized
403 → Forbidden
404 → Not Found
409 → Conflict
500 → Internal Server Error
```

### Interview Note

Do not return `200 OK` for every situation. Use HTTP semantics correctly.

---

# 4. DTO + Validation

### Topics
- Request DTO
- Response DTO
- Entity vs DTO
- `@Valid`
- `@NotNull`
- `@NotBlank`
- `@Size`
- `@Min`
- `@Max`
- `@Email`
- Custom validation

### Practical Flow

```text
Client
  ↓
Request DTO
  ↓
Validation
  ↓
Service
  ↓
Entity
  ↓
Repository
```

### Why DTO?

```text
Entity ≠ API Contract
```

DTOs provide encapsulation, security, API stability, and separation between persistence and presentation.

### Interview Answer

> DTOs prevent database entities from becoming direct API contracts and allow us to expose only the fields required by the client.

---

# 5. Exception Handling

### Topics
- Custom exceptions
- `@ExceptionHandler`
- `@ControllerAdvice`
- Global exception handling
- Validation errors
- Standard error responses

### Practical Example

```http
GET /products/100
```

```json
{
  "status": 404,
  "message": "Product not found"
}
```

Flow:

```text
Controller
    ↓
Service
    ↓
Business Exception
    ↓
Global Exception Handler
    ↓
Standard Error Response
```

### Interview Note

> Global exception handling centralizes error handling and gives clients consistent responses without repeating try/catch blocks in every controller.

---

# 6. Spring Data JPA + Hibernate + ORM

This is a major section of the project.

### Topics
- JPA
- Hibernate
- ORM
- Entities
- Primary keys
- `@OneToOne`
- `@OneToMany`
- `@ManyToOne`
- `@ManyToMany`
- Cascade
- Lazy vs Eager
- Transactions
- JPQL
- Native queries
- `@Query`
- Pagination
- Sorting
- Entity lifecycle
- First-level cache
- N+1 problem

### Relationship Example

```text
Customer
   |
   | 1
   |
   | *
   ↓
Orders
```

### JPA vs Hibernate

```text
JPA       = Specification
Hibernate = Implementation
```

### Interview Note

> JPA defines the ORM API/specification, while Hibernate is a popular implementation of that specification.

---

# 7. Pagination + Sorting

Imagine 100,000 products.

Bad:

```text
Database
   ↓
100,000 records
   ↓
Application memory
```

Better:

```text
Page 0 → 20
Page 1 → 20
Page 2 → 20
```

Example:

```http
GET /products?page=0&size=20&sort=price,asc
```

### Interview Answer

> Pagination reduces the amount of data fetched and transferred per request, improving memory usage, response time, and scalability.

---

# 8. JPA N+1 Problem

Suppose we retrieve customers and their orders.

Bad scenario:

```text
1 query → customers

N queries → orders for each customer

Total = N + 1 queries
```

### Solutions

Study and implement:

- `JOIN FETCH`
- Entity Graph
- Batch fetching
- DTO projections
- Query optimization

### Interview Answer

> N+1 occurs when one query retrieves parent records and an additional query is executed for each parent to load associated data. Fetch joins, entity graphs, batching, or DTO projections can solve it depending on the use case.

---

# 9. Lazy vs Eager Loading

### Lazy

```text
Customer loaded
     ↓
Orders not loaded yet
     ↓
getOrders()
     ↓
Orders loaded
```

### Eager

```text
Customer loaded
     ↓
Orders loaded immediately
```

### Important Note

Avoid blindly choosing `EAGER`. Prefer controlled fetching based on the query/use case, especially in larger applications.

---

# 10. Transactions

### Topics
- `@Transactional`
- ACID
- Commit
- Rollback
- Isolation
- Propagation
- Read-only transactions

### Practical Example

```text
Create Order
    ↓
Create Order Items
    ↓
Update Inventory
```

If a required operation fails:

```text
Rollback
```

### Interview Topics

Know:

```text
Atomicity
Consistency
Isolation
Durability
```

Also prepare common isolation levels:

```text
READ_UNCOMMITTED
READ_COMMITTED
REPEATABLE_READ
SERIALIZABLE
```

---

# 11. JUnit 5 + Mockito

### Topics
- `@Test`
- `@BeforeEach`
- `@AfterEach`
- `@BeforeAll`
- `@AfterAll`
- `@Mock`
- `@InjectMocks`
- `@Spy`
- `@Captor`
- `when()`
- `thenReturn()`
- `thenThrow()`
- `verify()`
- Argument matchers
- Exception testing
- Parameterized tests
- Async testing
- MockMvc
- Spring test context

### Practical Structure

```text
ProductService
      |
      ↓
Mock ProductRepository
```

### Important Differences

`@Mock`

> Creates a mock dependency.

`@InjectMocks`

> Creates the class under test and injects the mocks.

`verify()`

> Verifies that a method was called.

Example:

```java
verify(repository).save(product);
```

### Interview Answer

> Mockito isolates the class under test by replacing its dependencies with controlled mock behavior.

---

# 12. SOLID Principles

## S — Single Responsibility

Bad:

```text
OrderService
 ├── Save order
 ├── Send email
 ├── Generate PDF
 ├── Send notification
 └── Payment processing
```

Better:

```text
OrderService
EmailService
PdfService
NotificationService
PaymentService
```

---

## O — Open/Closed

Open for extension, closed for modification.

Example:

```text
Payment
 ├── CardPayment
 ├── UpiPayment
 └── PaypalPayment
```

Adding a payment type should not require rewriting existing business logic.

---

## L — Liskov Substitution

Subtypes should be usable wherever their parent abstraction is expected without breaking correctness.

---

## I — Interface Segregation

Prefer small focused interfaces over large interfaces containing unrelated methods.

---

## D — Dependency Inversion

High-level business logic should depend on abstractions.

```text
OrderService
      ↓
PaymentService interface
      ↓
 ┌────┴─────┐
Card       UPI
```

### Interview Rule

For SOLID questions, always explain:

```text
Principle
  ↓
Bad Code
  ↓
Problem
  ↓
Better Code
  ↓
Real Project Example
```

---

# 13. Design Principles

Implement and understand:

- DRY — Don't Repeat Yourself
- KISS — Keep It Simple
- YAGNI — You Aren't Gonna Need It
- Separation of Concerns
- Encapsulation
- High Cohesion
- Loose Coupling
- Composition over Inheritance
- Program to an interface

### Important Note

Do not over-engineer a simple application just to use a pattern. Choose an abstraction because the requirement needs it.

---

# 14. Design Patterns

The repository should demonstrate patterns through real business problems.

## Strategy Pattern

Use when an algorithm/behavior changes.

```text
PaymentStrategy
     ├── CardPayment
     ├── UpiPayment
     └── PaypalPayment
```

Useful for:

- Payment
- Discounts
- Shipping calculation
- Notification channels

---

## Factory Pattern

Use when object creation depends on a type.

```text
PaymentFactory
     ├── CardPayment
     ├── UpiPayment
     └── PaypalPayment
```

---

## Builder Pattern

Useful for complex objects:

```java
Order.builder()
     .customerId(id)
     .items(items)
     .address(address)
     .paymentType(type)
     .build();
```

---

## Adapter Pattern

Useful for integrating incompatible third-party APIs.

```text
Application Interface
        ↓
     Adapter
        ↓
External Payment API
```

---

## Observer Pattern

Useful when one event triggers multiple independent actions.

```text
Order Created
     ├── Notification
     ├── Email
     └── Analytics
```

This leads naturally toward event-driven microservices.

---

## Singleton

Spring beans are singleton-scoped by default.

Important interview point:

> Spring's singleton bean scope means one bean instance per Spring container. It is not identical to manually implementing the GoF Singleton pattern.

---

# 15. Spring Actuator

### Topics
- `/actuator/health`
- `/actuator/info`
- `/actuator/metrics`
- Health indicators
- Custom health indicators
- Metrics
- Application information

### Practical Example

```text
Application
     ↓
Actuator
     ├── Health
     ├── Metrics
     └── Info
```

Example:

```http
GET /actuator/health
```

```json
{
  "status": "UP"
}
```

### Why?

A monitoring system or orchestrator can ask:

```text
Is this application healthy?
```

### Interview Answer

> Spring Boot Actuator exposes production-oriented endpoints for health, metrics, and application monitoring.

---

# 16. Microservices Architecture

Split business capabilities into independently deployable services.

Example:

```text
API Gateway
Authentication Service
Customer Service
Restaurant Service
Order Service
Payment Service
Notification Service
```

### Final Architecture

```text
                         CLIENT
                           |
                           ↓
                     API GATEWAY
                           |
                           ↓
                    EUREKA SERVER
                           |
             +-------------+-------------+
             |             |             |
             ↓             ↓             ↓
       Order Service Restaurant Service Other Services
             |
             | Feign
             ↓
       Circuit Breaker
             |
             ↓
       Restaurant Service
```

---

# 17. Database Per Service

Instead of:

```text
All Services
     ↓
One Database
```

Use:

```text
Order Service       → Order DB
Restaurant Service  → Restaurant DB
Customer Service    → Customer DB
```

### Why?

- Loose coupling
- Independent deployment
- Independent scaling
- Data ownership
- Fault isolation

A service should access another service's data through its API, not by directly querying its database.

---

# 18. Eureka Service Discovery

Without discovery:

```text
Order Service
     ↓
http://localhost:8082
```

With discovery:

```text
Order Service
     ↓
RESTAURANT-SERVICE
     ↓
Eureka
     ↓
Restaurant instance
```

### Practical Implementation

Start:

```text
Eureka Server
Restaurant Service
Order Service
```

Register both services and verify service discovery.

### Interview Answer

> Service discovery allows services to locate service instances dynamically instead of hardcoding host and port information.

---

# 19. API Gateway

Instead of exposing every service directly:

```text
Client → Order Service
Client → Restaurant Service
Client → Customer Service
```

Use:

```text
Client
   ↓
API Gateway
   ├── Order
   ├── Restaurant
   └── Customer
```

### Gateway Responsibilities

- Routing
- Authentication
- Authorization
- Rate limiting
- Request filtering
- Logging
- Cross-cutting concerns

---

# 20. OpenFeign

Use Feign for synchronous service-to-service communication.

```text
Order Service
      |
      | Feign
      ↓
Restaurant Service
```

Conceptually:

```java
@FeignClient(name = "RESTAURANT-SERVICE")
```

### Interview Answer

> OpenFeign provides a declarative HTTP client, allowing service-to-service APIs to be represented as Java interfaces rather than manually constructing HTTP calls.

---

# 21. Resilience4j Circuit Breaker

This is the main fault-tolerance practical.

Scenario:

```text
Order Service
      |
      | Feign
      ↓
Restaurant Service
```

Restaurant Service becomes unavailable.

### Without Circuit Breaker

```text
Request
  ↓
Order
  ↓
Restaurant
  ↓
Timeout
  ↓
Failure
```

Repeated requests can consume threads and resources.

### With Circuit Breaker

```text
Request
  ↓
Order
  ↓
Circuit Breaker
  ↓
Restaurant
```

After failures cross the threshold:

```text
CLOSED
   ↓
OPEN
   ↓
Fallback
```

---

# 22. Circuit Breaker Practical Experiment

This should be performed manually.

### Step 1

Start:

```text
Eureka
Gateway
Order Service
Restaurant Service
```

### Step 2

Call an Order endpoint that needs Restaurant Service.

Verify success.

### Step 3

Stop Restaurant Service.

### Step 4

Call the Order API repeatedly.

Observe:

```text
Failures
   ↓
Configured threshold
   ↓
Circuit OPEN
```

### Step 5

Call again.

The request should fail fast or use the fallback rather than continuously waiting for the unavailable service.

### Step 6

Restart Restaurant Service.

After the configured wait duration:

```text
OPEN
  ↓
HALF_OPEN
  ↓
Test call
  ↓
Success
  ↓
CLOSED
```

This experiment is excellent interview material because it demonstrates the concept rather than only defining it.

---

# 23. Retry vs Circuit Breaker

## Retry

```text
Request
  ↓
Failure
  ↓
Retry
  ↓
Success
```

Useful for transient failures.

## Circuit Breaker

```text
Repeated failures
       ↓
Circuit OPEN
       ↓
Stop calling dependency
```

### Interview Answer

> Retry attempts an operation again when a failure may be temporary. Circuit Breaker prevents repeated calls to an unhealthy dependency after failures cross a configured threshold.

---

# 24. Fault Tolerance

Study these together:

```text
Retry
Timeout
Circuit Breaker
Bulkhead
Rate Limiting
Fallback
```

They solve different failure scenarios.

Example:

```text
Downstream problem
      ├── Timeout → don't wait forever
      ├── Retry → temporary failure
      ├── Circuit Breaker → stop repeated failures
      ├── Bulkhead → isolate resources
      └── Fallback → graceful degraded response
```

---

# 25. Production-Oriented Extensions

After completing the core project, add:

### Security

```text
JWT
OAuth2
Role-based authorization
```

### Observability

```text
Actuator
Metrics
Centralized logging
Correlation IDs
Distributed tracing
```

### Messaging

```text
Kafka
RabbitMQ
```

### Deployment

```text
Docker
Kubernetes
CI/CD
```

### Database

```text
Flyway / Liquibase
Connection pooling
Indexes
Query optimization
```

---

# 26. Practical Development Checklist

## Spring Boot
- [ ] Create application
- [ ] Dependency Injection
- [ ] Controller
- [ ] Service
- [ ] Repository
- [ ] Configuration
- [ ] Profiles
- [ ] Auto-configuration

## REST
- [ ] GET
- [ ] POST
- [ ] PUT
- [ ] PATCH
- [ ] DELETE
- [ ] PathVariable
- [ ] RequestParam
- [ ] RequestBody
- [ ] ResponseEntity
- [ ] HTTP status codes
- [ ] Idempotency

## Validation / Errors
- [ ] DTO
- [ ] Bean Validation
- [ ] Custom validation
- [ ] Custom exceptions
- [ ] ControllerAdvice
- [ ] Standard error response

## JPA
- [ ] Entity
- [ ] Relationships
- [ ] Lazy / Eager
- [ ] Cascade
- [ ] Transactions
- [ ] JPQL
- [ ] `@Query`
- [ ] Pagination
- [ ] Sorting
- [ ] N+1
- [ ] Fetch Join
- [ ] Entity Graph
- [ ] DTO Projection

## Testing
- [ ] JUnit 5
- [ ] Mockito
- [ ] `@Mock`
- [ ] `@InjectMocks`
- [ ] `@Spy`
- [ ] `verify`
- [ ] Exception tests
- [ ] Parameterized tests
- [ ] MockMvc
- [ ] Unit tests
- [ ] Integration tests
- [ ] Async method tests

## Design
- [ ] SOLID
- [ ] DRY
- [ ] KISS
- [ ] YAGNI
- [ ] Strategy
- [ ] Factory
- [ ] Builder
- [ ] Adapter
- [ ] Observer

## Actuator
- [ ] Health
- [ ] Metrics
- [ ] Info
- [ ] Custom Health Indicator

## Microservices
- [ ] Service separation
- [ ] Database per service
- [ ] Eureka
- [ ] API Gateway
- [ ] Feign
- [ ] Circuit Breaker
- [ ] Fallback
- [ ] Retry
- [ ] Timeout

---

# 27. Interview Preparation Method

For every topic, prepare five answers:

```text
1. What is it?
        ↓
2. Why do we need it?
        ↓
3. How does it work?
        ↓
4. Where did you use it?
        ↓
5. What problem did it solve?
```

### Example — Circuit Breaker

**What?**

A fault-tolerance pattern.

**Why?**

To prevent repeated calls to an unhealthy downstream service.

**How?**

Through states:

```text
CLOSED → OPEN → HALF_OPEN
```

**Where?**

```text
Order Service
      ↓
Restaurant Service
```

**Problem solved?**

Prevents cascading failures and improves response behavior when a dependency is unavailable.

---

# 28. Important Interview Questions

## Spring Boot

- What is Spring Boot?
- What is auto-configuration?
- What is dependency injection?
- `@Component` vs `@Service` vs `@Repository`?
- Why constructor injection?
- What is a Spring Boot starter?
- What is the Bean lifecycle?
- Singleton vs Prototype?
- What is `@Configuration`?

## REST

- PUT vs PATCH?
- GET vs POST?
- What is idempotency?
- 401 vs 403?
- 400 vs 404?
- When should 201 be returned?
- How do you design a REST API?

## JPA / Hibernate

- ORM?
- JPA vs Hibernate?
- Lazy vs Eager?
- N+1 problem?
- How do you solve N+1?
- `@Transactional`?
- Transaction isolation levels?
- Cascade?
- `save()` vs `saveAndFlush()`?
- JPQL vs native SQL?
- Pagination?
- Entity vs DTO?
- First-level cache?

## JUnit / Mockito

- `@Mock` vs `@Spy`?
- `@Mock` vs `@InjectMocks`?
- `when()` vs `verify()`?
- How do you test exceptions?
- How do you test async methods?
- Unit vs integration test?
- `@SpringBootTest` vs `@WebMvcTest`?
- Why avoid excessive mocking?

## SOLID / Design

- Explain each SOLID principle.
- Give a real project example.
- Why does SRP matter?
- How does DIP improve testing?
- Strategy vs if/else?
- When would you use Factory?
- When would you use Adapter?

## Actuator

- What is Spring Actuator?
- Why is it needed?
- What is `/actuator/health`?
- What are metrics?
- How do you monitor a Spring Boot application?
- How do you create a custom health indicator?

## Microservices

- Monolith vs Microservices?
- Why microservices?
- Disadvantages?
- Service discovery?
- Eureka?
- API Gateway?
- Feign?
- Synchronous vs asynchronous communication?
- Database per service?
- Distributed transactions?
- Circuit Breaker?
- Retry?
- Timeout?
- Saga?
- CQRS?
- How do you handle service failure?
- How do you monitor microservices?

---

# 29. Interview Golden Rule

Do not answer with only a definition.

Instead of:

> "Circuit Breaker is a design pattern."

Say:

> "In the application, Order Service calls Restaurant Service using Feign. If Restaurant Service becomes unavailable, repeated calls can cause timeouts and resource exhaustion. I put a Resilience4j Circuit Breaker around that call. When failures cross the configured threshold, the circuit opens and requests use a fallback. After the wait duration it moves to half-open and tests whether the dependency has recovered."

This demonstrates:

```text
Concept
+
Implementation
+
Problem
+
Solution
+
Real-world usage
```

---

# 30. Architecture Memory Map

Remember the whole project using this picture:

```text
                    CLIENT
                       |
                       ↓
                API GATEWAY
                       |
                       ↓
                  EUREKA
                       |
             +---------+---------+
             |                   |
             ↓                   ↓
       ORDER SERVICE      RESTAURANT SERVICE
             |
             | Feign
             ↓
       CIRCUIT BREAKER
             |
             ↓
          FALLBACK
```

Inside each service:

```text
Controller
    ↓
DTO + Validation
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
Database
```

Cross-cutting:

```text
Actuator
   ↓
Health + Metrics

JUnit + Mockito
   ↓
Testing

SOLID + Design Patterns
   ↓
Maintainable Design
```

---

# 31. Recommended GitHub Repository Structure

```text
spring-boot-microservices-learning/
│
├── README.md
├── DEVELOPMENT_PLAN.md
│
├── 01-spring-boot/
├── 02-rest-api/
├── 03-validation-exception-handling/
├── 04-spring-data-jpa/
├── 05-pagination-sorting/
│
├── 06-jpa-performance/
│   ├── n-plus-one/
│   ├── fetch-join/
│   └── entity-graph/
│
├── 07-testing/
│   ├── junit5/
│   └── mockito/
│
├── 08-solid/
│
├── 09-design-patterns/
│   ├── strategy/
│   ├── factory/
│   ├── builder/
│   ├── adapter/
│   └── observer/
│
├── 10-actuator/
│
├── 11-microservices/
│   ├── eureka-server/
│   ├── api-gateway/
│   ├── order-service/
│   └── restaurant-service/
│
├── 12-feign/
│
├── 13-resilience4j/
│   └── circuit-breaker/
│
└── 14-production-concepts/
    ├── security/
    ├── observability/
    ├── messaging/
    └── deployment/
```

---

# 32. Final Learning Outcome

After completing the plan, the target is to move from:

> **"I know Spring Boot annotations."**

to:

> **"I can design, implement, test, monitor, troubleshoot, and explain a production-oriented Spring Boot microservices application."**

The most useful development cycle is:

```text
Learn
  ↓
Implement
  ↓
Test
  ↓
Break intentionally
  ↓
Observe failure
  ↓
Fix
  ↓
Optimize
  ↓
Explain in interview
```

Especially practice breaking and fixing:

```text
JPA N+1
Transactions
Exception Handling
Actuator health
Feign communication
Circuit Breaker
Downstream service failures
```
