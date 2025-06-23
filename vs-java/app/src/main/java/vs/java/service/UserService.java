package vs.java.service;

import org.springframework.stereotype.Service;
import vs.java.entity.User;
import vs.java.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByNameContaining(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public long getUserCount() {
        return userRepository.count();
    }
}