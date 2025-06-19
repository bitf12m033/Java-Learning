package vs.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vs.java.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}