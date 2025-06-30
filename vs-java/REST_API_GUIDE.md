# Spring Boot REST API Guide

This guide explains how to create and use REST APIs in Spring Boot applications.

## Table of Contents
1. [Overview](#overview)
2. [Key Concepts](#key-concepts)
3. [API Endpoints](#api-endpoints)
4. [Testing the APIs](#testing-the-apis)
5. [Best Practices](#best-practices)
6. [Common Patterns](#common-patterns)

## Overview

This Spring Boot application demonstrates REST API development with:
- **REST Controllers**: Handle HTTP requests and responses
- **DTOs**: Data Transfer Objects for consistent API responses
- **Exception Handling**: Global exception handling for error responses
- **CORS Support**: Cross-Origin Resource Sharing enabled
- **Database Integration**: JPA/Hibernate with MySQL

## Key Concepts

### 1. REST Controller Annotations

```java
@RestController                    // Marks class as REST controller
@RequestMapping("/api/users")      // Base URL for all endpoints
@CrossOrigin(origins = "*")        // Enable CORS
```

### 2. HTTP Method Annotations

```java
@GetMapping("/{id}")               // GET request
@PostMapping                       // POST request
@PutMapping("/{id}")              // PUT request
@PatchMapping("/{id}")            // PATCH request
@DeleteMapping("/{id}")           // DELETE request
```

### 3. Parameter Annotations

```java
@PathVariable Long id              // Path parameter
@RequestParam String name          // Query parameter
@RequestBody User user             // Request body (JSON)
```

## API Endpoints

### Base URLs

- **v1 API**: `http://localhost:8080/api/users`
- **v2 API**: `http://localhost:8080/api/v2/users` (Enhanced with ApiResponse wrapper)
- **v3 API**: `http://localhost:8080/api/v3/users` (Comprehensive with bulk operations)

### Basic API Endpoints (`/api`)

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/api/health` | Health check | Status info |
| GET | `/api/info` | Application info | Version, Java info |
| GET | `/api/greeting?name={name}` | Get greeting | Personalized greeting |
| POST | `/api/greeting` | Post greeting | JSON greeting |
| GET | `/api/datetime` | Current date/time | Formatted datetime |

### User Management API v1 (`/api/users`)

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/api/users` | Get all users | User list |
| GET | `/api/users/{id}` | Get user by ID | Single user |
| POST | `/api/users` | Create user | Created user |
| PUT | `/api/users/{id}` | Update user | Updated user |
| PATCH | `/api/users/{id}` | Partial update | Updated user |
| DELETE | `/api/users/{id}` | Delete user | No content |
| GET | `/api/users/search?name={name}` | Search users | Filtered users |
| GET | `/api/users/count` | User count | Total count |

### Enhanced User API v2 (`/api/v2/users`)

Same endpoints as v1 but with enhanced response format using `ApiResponse<T>` wrapper.

### Comprehensive User API v3 (`/api/v3/users`)

Same endpoints as v2 but with bulk operations and advanced features.

## Testing the APIs

### 1. Using the Test Page

1. Start the application: `./gradlew bootRun`
2. Open browser: `http://localhost:8080/api-test.html`
3. Test all endpoints interactively

### 2. Using cURL

```bash
# Health check
curl http://localhost:8080/api/health

# Get greeting
curl "http://localhost:8080/api/greeting?name=John"

# Create user
curl -X POST http://localhost:8080/api/v2/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'

# Get all users
curl http://localhost:8080/api/v2/users

# Get user by ID
curl http://localhost:8080/api/v2/users/1

# Update user
curl -X PUT http://localhost:8080/api/v2/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","email":"jane@example.com"}'

# Delete user
curl -X DELETE http://localhost:8080/api/v2/users/1
```

### 3. Using Postman

Import these requests:

```json
{
  "info": {
    "name": "Spring Boot API Tests",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/health"
      }
    },
    {
      "name": "Create User",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/api/v2/users",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"John Doe\",\n  \"email\": \"john@example.com\"\n}"
        }
      }
    }
  ]
}
```

## Response Formats

### Standard Response (v1)
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "imagePath": "/images/abc123.jpg"
}
```

### Enhanced Response (v2, v3)
```json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "imagePath": "/images/abc123.jpg"
  }
}
```

### Error Response
```json
{
  "success": false,
  "message": "User not found with ID: 999",
  "data": null,
  "timestamp": "2024-01-15T14:30:00"
}
```

## Best Practices

### 1. HTTP Status Codes

```java
// Success responses
ResponseEntity.ok(data)                    // 200 OK
ResponseEntity.status(HttpStatus.CREATED).body(data)  // 201 Created
ResponseEntity.noContent().build()         // 204 No Content

// Error responses
ResponseEntity.notFound().build()          // 404 Not Found
ResponseEntity.badRequest().body(error)    // 400 Bad Request
ResponseEntity.status(HttpStatus.CONFLICT).body(error) // 409 Conflict
```

### 2. Consistent Response Format

Use DTOs for consistent API responses:

```java
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
}
```

### 3. Exception Handling

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Handle not found errors
    }
}
```

### 4. Input Validation

```java
@PostMapping
public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
    // @Valid triggers validation
}
```

### 5. API Versioning

```java
@RequestMapping("/api/v1/users")  // Version 1
@RequestMapping("/api/v2/users")  // Version 2
```

## Common Patterns

### 1. CRUD Operations

```java
// Create
@PostMapping
public ResponseEntity<T> create(@RequestBody T entity)

// Read
@GetMapping("/{id}")
public ResponseEntity<T> getById(@PathVariable Long id)

// Update
@PutMapping("/{id}")
public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T entity)

// Delete
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id)
```

### 2. Search and Filtering

```java
@GetMapping("/search")
public ResponseEntity<List<T>> search(@RequestParam String query)
```

### 3. Pagination

```java
@GetMapping
public ResponseEntity<Page<T>> getAll(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
)
```

### 4. Sorting

```java
@GetMapping
public ResponseEntity<List<T>> getAll(
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "asc") String direction
)
```

## Security Considerations

### 1. CORS Configuration

```java
@CrossOrigin(origins = "http://localhost:3000")  // Specific origin
@CrossOrigin(origins = "*")                      // All origins (development only)
```

### 2. Input Sanitization

```java
// Validate and sanitize input
@Valid @RequestBody User user
```

### 3. Rate Limiting

Consider adding rate limiting for production APIs.

## Database Integration

### Repository Methods

```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

### Service Layer

```java
@Service
public class UserService {
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
```

## Testing

### Unit Tests

```java
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {
    @Test
    void shouldReturnUserWhenValidId() {
        // Test implementation
    }
}
```

### Integration Tests

```java
@SpringBootTest
@AutoConfigureTestDatabase
class UserApiIntegrationTest {
    @Test
    void shouldCreateUser() {
        // Test implementation
    }
}
```

This guide covers the essential concepts and patterns for building REST APIs in Spring Boot. The application demonstrates these concepts with working examples that you can test and extend. 