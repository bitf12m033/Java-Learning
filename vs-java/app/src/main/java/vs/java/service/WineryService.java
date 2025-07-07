package vs.java.service;

import org.springframework.stereotype.Service;
import vs.java.entity.Winery;
import vs.java.repository.WineryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WineryService {

    private final WineryRepository wineryRepository;

    public WineryService(WineryRepository wineryRepository) {
        this.wineryRepository = wineryRepository;
    }

    public List<Winery> getAllWineries() {
        return wineryRepository.findAll();
    }

    public Optional<Winery> getWineryById(String id) {
        return wineryRepository.findById(id);
    }

    public long getWineryCount() {
        return wineryRepository.count();
    }

    public List<Winery> searchWineriesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllWineries();
        }
        return wineryRepository.findByWineryNameContainingIgnoreCase(name.trim());
    }

    public List<Winery> searchWineriesByLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return getAllWineries();
        }
        return wineryRepository.searchByLocation(location.trim());
    }
} 