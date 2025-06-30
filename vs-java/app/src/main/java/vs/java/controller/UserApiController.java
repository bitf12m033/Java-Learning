package vs.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vs.java.dto.ApiResponse;
import vs.java.entity.User;
import vs.java.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v3/users")
@CrossOrigin(origins = "*")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    // ==================== CREATE OPERATIONS ====================

    // POST /api/v3/users - Create user with JSON data only
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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

    // POST /api/v3/users/with-image - Create user with image upload
    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> createUserWithImage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            
            User savedUser = userService.saveUserWithImage(user, image);
            ApiResponse<User> response = ApiResponse.success("User created successfully with image", savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<User> response = ApiResponse.error("Failed to create user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // ==================== READ OPERATIONS ====================

    // GET /api/v3/users - Get all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            ApiResponse<List<User>> response = ApiResponse.success("Users retrieved successfully", users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<User>> response = ApiResponse.error("Failed to retrieve users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/v3/users/{id} - Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                ApiResponse<User> response = ApiResponse.success("User found", user.get());
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<User> response = ApiResponse.error("User not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<User> response = ApiResponse.error("Failed to retrieve user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/v3/users/search?name={name} - Search users by name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> searchUsersByName(@RequestParam String name) {
        try {
            List<User> users = userService.findUsersByNameContaining(name);
            ApiResponse<List<User>> response = ApiResponse.success("Search completed", users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<User>> response = ApiResponse.error("Failed to search users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/v3/users/count - Get total user count
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getUserCount() {
        try {
            long count = userService.getUserCount();
            ApiResponse<Long> response = ApiResponse.success("User count retrieved", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Long> response = ApiResponse.error("Failed to get user count: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== UPDATE OPERATIONS ====================

    // PUT /api/v3/users/{id} - Update user with JSON data only
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
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
        } catch (Exception e) {
            ApiResponse<User> response = ApiResponse.error("Failed to update user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // PUT /api/v3/users/{id}/with-image - Update user with image upload
    @PutMapping(value = "/{id}/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> updateUserWithImage(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "removeImage", required = false, defaultValue = "false") boolean removeImage) {
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            
            User updatedUser = userService.updateUserWithImage(id, user, image, removeImage);
            ApiResponse<User> response = ApiResponse.success("User updated successfully with image", updatedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<User> response = ApiResponse.error("Failed to update user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // PATCH /api/v3/users/{id} - Partial update
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<User>> partialUpdateUser(@PathVariable Long id, @RequestBody User userUpdates) {
        try {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                
                // Update only non-null fields
                if (userUpdates.getName() != null) {
                    user.setName(userUpdates.getName());
                }
                if (userUpdates.getEmail() != null) {
                    user.setEmail(userUpdates.getEmail());
                }
                
                User updatedUser = userService.saveUser(user);
                ApiResponse<User> response = ApiResponse.success("User partially updated successfully", updatedUser);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<User> response = ApiResponse.error("User not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<User> response = ApiResponse.error("Failed to update user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // ==================== DELETE OPERATIONS ====================

    // DELETE /api/v3/users/{id} - Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                userService.deleteUserWithImage(id);
                ApiResponse<Void> response = ApiResponse.success("User deleted successfully", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Void> response = ApiResponse.error("User not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Void> response = ApiResponse.error("Failed to delete user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/v3/users - Delete all users (dangerous operation)
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                userService.deleteUserWithImage(user.getId());
            }
            ApiResponse<Void> response = ApiResponse.success("All users deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Void> response = ApiResponse.error("Failed to delete all users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== BULK OPERATIONS ====================

    // POST /api/v3/users/bulk - Create multiple users
    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<User>>> createBulkUsers(@RequestBody List<User> users) {
        try {
            List<User> savedUsers = users.stream()
                    .map(userService::saveUser)
                    .toList();
            ApiResponse<List<User>> response = ApiResponse.success("Bulk users created successfully", savedUsers);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<List<User>> response = ApiResponse.error("Failed to create bulk users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // PUT /api/v3/users/bulk - Update multiple users
    @PutMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<User>>> updateBulkUsers(@RequestBody List<User> users) {
        try {
            List<User> updatedUsers = users.stream()
                    .map(user -> {
                        if (user.getId() != null) {
                            return userService.saveUser(user);
                        }
                        return user;
                    })
                    .toList();
            ApiResponse<List<User>> response = ApiResponse.success("Bulk users updated successfully", updatedUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<User>> response = ApiResponse.error("Failed to update bulk users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
} 