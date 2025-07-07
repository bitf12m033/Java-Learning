package vs.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vs.java.entity.User;
import vs.java.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final String uploadDir = "uploads/images/";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User saveUserWithImage(User user, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                // Validate file type
                String contentType = image.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("File must be an image");
                }

                // Generate unique filename
                String originalFilename = image.getOriginalFilename();
                if (originalFilename == null || originalFilename.isEmpty()) {
                    throw new IllegalArgumentException("Invalid filename");
                }
                
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String filename = UUID.randomUUID().toString() + fileExtension;
                
                // Ensure upload directory exists
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Save file
                Path filePath = uploadPath.resolve(filename);
                Files.copy(image.getInputStream(), filePath);
                
                // Set image path in user entity
                user.setImagePath("/images/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }
        
        return userRepository.save(user);
    }

    public User updateUserWithImage(Long id, User updatedUser, MultipartFile image, boolean removeImage) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update basic fields
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        // Handle image removal
        if (removeImage) {
            deleteImageFile(existingUser.getImagePath());
            existingUser.setImagePath(null);
        }

        // Handle new image upload
        if (image != null && !image.isEmpty()) {
            try {
                // Validate file type
                String contentType = image.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("File must be an image");
                }

                // Delete old image if exists
                if (existingUser.getImagePath() != null) {
                    deleteImageFile(existingUser.getImagePath());
                }

                // Generate unique filename
                String originalFilename = image.getOriginalFilename();
                if (originalFilename == null || originalFilename.isEmpty()) {
                    throw new IllegalArgumentException("Invalid filename");
                }
                
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String filename = UUID.randomUUID().toString() + fileExtension;
                
                // Ensure upload directory exists
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Save new file
                Path filePath = uploadPath.resolve(filename);
                Files.copy(image.getInputStream(), filePath);
                
                // Set new image path
                existingUser.setImagePath("/images/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }

        return userRepository.save(existingUser);
    }

    public void deleteUserWithImage(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Delete image file if exists
        if (user.getImagePath() != null) {
            deleteImageFile(user.getImagePath());
        }

        // Delete user from database
        userRepository.deleteById(id);
    }

    private void deleteImageFile(String imagePath) {
        if (imagePath != null && imagePath.startsWith("/images/")) {
            try {
                String filename = imagePath.substring("/images/".length());
                Path filePath = Paths.get(uploadDir, filename);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                // Log error but don't throw - don't fail user deletion if image deletion fails
                System.err.println("Failed to delete image file: " + imagePath + " - " + e.getMessage());
            }
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByNameContaining(String name) {
        return userRepository.findByNameContainingIgnoreCaseOrderByName(name);
    }

    public long getUserCount() {
        return userRepository.count();
    }
}