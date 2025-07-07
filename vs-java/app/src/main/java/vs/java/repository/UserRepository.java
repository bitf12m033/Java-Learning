package vs.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vs.java.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find users by name containing (case-insensitive)
    List<User> findByNameContainingIgnoreCaseOrderByName(String name);

    // Find user by email
    User findByEmail(String email);

    // Check if user exists by email
    boolean existsByEmail(String email);

    // Custom query for more complex searches
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY u.name")
    List<User> searchByNameOrEmail(@Param("searchTerm") String searchTerm);
}