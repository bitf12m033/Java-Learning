package vs.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vs.java.entity.Winery;

import java.util.List;

@Repository
public interface WineryRepository extends JpaRepository<Winery, String> {

    // Find wineries by name containing (case-insensitive)
    @Query("SELECT w FROM Winery w WHERE LOWER(w.wineryName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY w.wineryName")
    List<Winery> findByWineryNameContainingIgnoreCase(@Param("name") String name);

    // Find wineries by city containing (case-insensitive)
    @Query("SELECT w FROM Winery w WHERE LOWER(w.City) LIKE LOWER(CONCAT('%', :city, '%')) ORDER BY w.wineryName")
    List<Winery> findByCityContainingIgnoreCase(@Param("city") String city);

    // Find wineries by state containing (case-insensitive)
    @Query("SELECT w FROM Winery w WHERE LOWER(w.State) LIKE LOWER(CONCAT('%', :state, '%')) ORDER BY w.wineryName")
    List<Winery> findByStateContainingIgnoreCase(@Param("state") String state);

    // Custom query for location search (city or state)
    @Query("SELECT w FROM Winery w WHERE LOWER(w.City) LIKE LOWER(CONCAT('%', :location, '%')) OR LOWER(w.State) LIKE LOWER(CONCAT('%', :location, '%')) ORDER BY w.wineryName")
    List<Winery> searchByLocation(@Param("location") String location);

    // Custom query for complex search
    @Query("SELECT w FROM Winery w WHERE LOWER(w.wineryName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(w.City) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(w.State) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY w.wineryName")
    List<Winery> searchByWineryNameOrLocation(@Param("searchTerm") String searchTerm);
} 