package vs.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vs.java.dto.ApiResponse;
import vs.java.entity.User;
import vs.java.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/users")
@CrossOrigin(origins = "*")
public class EnhancedUserRestController {

    private final UserService userService;

    public EnhancedUserRestController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/v2/users - Get all users with enhanced response
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = ApiResponse.success("Users retrieved successfully", users);
        return ResponseEntity.ok(response);
    }

    // GET /api/v2/users/{id} - Get user by ID with enhanced response
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            ApiResponse<User> response = ApiResponse.success("User found", user.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<User> response = ApiResponse.error("User not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // POST /api/v2/users - Create new user with enhanced response
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            ApiResponse<User> response = ApiResponse.success("User created successfully", savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<User> response = ApiResponse.error("Failed to create user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // PUT /api/v2/users/{id} - Update existing user with enhanced response
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            user.setId(id);
            User updatedUser = userService.saveUser(user);
            ApiResponse<User> response = ApiResponse.success("User updated successfully", updatedUser);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<User> response = ApiResponse.error("User not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // DELETE /api/v2/users/{id} - Delete user with enhanced response
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            ApiResponse<Void> response = ApiResponse.success("User deleted successfully", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Void> response = ApiResponse.error("User not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // GET /api/v2/users/search?name={name} - Search users by name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> searchUsersByName(@RequestParam String name) {
        List<User> users = userService.findUsersByNameContaining(name);
        ApiResponse<List<User>> response = ApiResponse.success("Search completed", users);
        return ResponseEntity.ok(response);
    }

    // GET /api/v2/users/count - Get total user count
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getUserCount() {
        long count = userService.getUserCount();
        ApiResponse<Long> response = ApiResponse.success("User count retrieved", count);
        return ResponseEntity.ok(response);
    }
} 