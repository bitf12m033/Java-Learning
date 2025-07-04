package vs.java.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import vs.java.entity.Winery;

import java.util.List;
import java.util.Optional;

@Repository
public class WineryRepository {

    private final JdbcTemplate jdbcTemplate;

    public WineryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to convert database rows to Winery objects
    private final RowMapper<Winery> wineryRowMapper = (rs, rowNum) -> {
        Winery winery = new Winery();
        winery.setWineryID(rs.getString("wineryID"));
        winery.setWineryName(rs.getString("wineryName"));
        winery.setAbbreviatedName(rs.getString("abbreviatedName"));
        winery.setAddress(rs.getString("Address"));
        winery.setCity(rs.getString("City"));
        winery.setState(rs.getString("State"));
        winery.setZipCode(rs.getString("ZipCode"));
        return winery;
    };

    public List<Winery> findAll() {
        String sql = "SELECT wineryID, wineryName, abbreviatedName, Address, City, State, ZipCode FROM Wineries ORDER BY wineryName";
        return jdbcTemplate.query(sql, wineryRowMapper);
    }

    public Optional<Winery> findById(String id) {
        String sql = "SELECT wineryID, wineryName, abbreviatedName, Address, City, State, ZipCode FROM Wineries WHERE wineryID = ?";
        List<Winery> wineries = jdbcTemplate.query(sql, wineryRowMapper, id);
        return wineries.isEmpty() ? Optional.empty() : Optional.of(wineries.get(0));
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM Wineries";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<Winery> findByNameContainingIgnoreCase(String name) {
        String sql = "SELECT wineryID, wineryName, abbreviatedName, Address, City, State, ZipCode FROM Wineries WHERE LOWER(wineryName) LIKE LOWER(?) ORDER BY wineryName";
        String searchPattern = "%" + name + "%";
        return jdbcTemplate.query(sql, wineryRowMapper, searchPattern);
    }

    public List<Winery> findByLocationContainingIgnoreCase(String location) {
        String sql = "SELECT wineryID, wineryName, abbreviatedName, Address, City, State, ZipCode FROM Wineries WHERE LOWER(City) LIKE LOWER(?) OR LOWER(State) LIKE LOWER(?) ORDER BY wineryName";
        String searchPattern = "%" + location + "%";
        return jdbcTemplate.query(sql, wineryRowMapper, searchPattern, searchPattern);
    }
} 