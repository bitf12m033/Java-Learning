package vs.java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vs.java.entity.Winery;
import vs.java.service.WineryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wineries")
@CrossOrigin(origins = "*")
public class WineryController {

    private final WineryService wineryService;

    public WineryController(WineryService wineryService) {
        this.wineryService = wineryService;
    }

    @GetMapping
    public ResponseEntity<List<Winery>> getAllWineries() {
        List<Winery> wineries = wineryService.getAllWineries();
        return ResponseEntity.ok(wineries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Winery> getWineryById(@PathVariable String id) {
        try {
            Optional<Winery> winery = wineryService.getWineryById(id);
            return winery.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getWineryCount() {
        long count = wineryService.getWineryCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Winery>> searchWineries(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location) {
        
        List<Winery> wineries;
        if (name != null && !name.trim().isEmpty()) {
            wineries = wineryService.searchWineriesByName(name);
        } else if (location != null && !location.trim().isEmpty()) {
            wineries = wineryService.searchWineriesByLocation(location);
        } else {
            wineries = wineryService.getAllWineries();
        }
        
        return ResponseEntity.ok(wineries);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Winery>> searchWineriesByName(@RequestParam String name) {
        List<Winery> wineries = wineryService.searchWineriesByName(name);
        return ResponseEntity.ok(wineries);
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<Winery>> searchWineriesByLocation(@RequestParam String location) {
        List<Winery> wineries = wineryService.searchWineriesByLocation(location);
        return ResponseEntity.ok(wineries);
    }
} 