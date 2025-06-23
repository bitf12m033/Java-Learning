package vs.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vs.java.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);
}