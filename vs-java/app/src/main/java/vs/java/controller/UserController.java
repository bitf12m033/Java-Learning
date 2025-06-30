package vs.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vs.java.entity.User;
import vs.java.service.UserService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE - Show form
    @GetMapping("/users/new")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    // CREATE - Save user
    @PostMapping("/users")
    public String createUser(@ModelAttribute User user, 
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.saveUserWithImage(user, image);
            redirectAttributes.addFlashAttribute("success", "User created successfully!");
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating user: " + e.getMessage());
            return "redirect:/users/new";
        }
    }

    // READ - List all users
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    // READ - Show single user
    @GetMapping("/users/{id}")
    public String showUser(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("user", user);
            return "user-detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "User not found: " + e.getMessage());
            return "redirect:/users";
        }
    }

    // UPDATE - Show edit form
    @GetMapping("/users/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("user", user);
            return "user-edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "User not found: " + e.getMessage());
            return "redirect:/users";
        }
    }

    // UPDATE - Save updated user
    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable Long id,
                           @ModelAttribute User user,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           @RequestParam(value = "removeImage", required = false) String removeImage,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserWithImage(id, user, image, "on".equals(removeImage));
            redirectAttributes.addFlashAttribute("success", "User updated successfully!");
            return "redirect:/users/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating user: " + e.getMessage());
            return "redirect:/users/" + id + "/edit";
        }
    }

    // DELETE - Show delete confirmation
    @GetMapping("/users/{id}/delete")
    public String showDeleteConfirmation(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("user", user);
            return "user-delete";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "User not found: " + e.getMessage());
            return "redirect:/users";
        }
    }

    // DELETE - Delete user
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserWithImage(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
            return "redirect:/users/" + id;
        }
    }

    // Image serving endpoint
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Path file = Paths.get("uploads/images/" + filename);
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
            }
        } catch (IOException e) {
            // Handle exception
        }
        return ResponseEntity.notFound().build();
    }
}