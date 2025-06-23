package vs.java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vs.java.service.DateTimeService;
import vs.java.service.GreetingService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    private final GreetingService greetingService;
    private final DateTimeService dateTimeService;

    public ApiController(GreetingService greetingService, DateTimeService dateTimeService) {
        this.greetingService = greetingService;
        this.dateTimeService = dateTimeService;
    }

    // GET /api/greeting?name={name} - Get greeting
    @GetMapping("/greeting")
    public ResponseEntity<Map<String, String>> getGreeting(@RequestParam(defaultValue = "World") String name) {
        Map<String, String> response = new HashMap<>();
        response.put("greeting", greetingService.getGreeting(name));
        response.put("name", name);
        return ResponseEntity.ok(response);
    }

    // POST /api/greeting - Get greeting with JSON body
    @PostMapping("/greeting")
    public ResponseEntity<Map<String, String>> postGreeting(@RequestBody Map<String, String> request) {
        String name = request.getOrDefault("name", "World");
        Map<String, String> response = new HashMap<>();
        response.put("greeting", greetingService.getGreeting(name));
        response.put("name", name);
        return ResponseEntity.ok(response);
    }

    // GET /api/datetime - Get current date and time
    @GetMapping("/datetime")
    public ResponseEntity<Map<String, String>> getDateTime() {
        Map<String, String> response = new HashMap<>();
        response.put("datetime", dateTimeService.getCurrentDateTime());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }

    // GET /api/health - Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running");
        response.put("timestamp", dateTimeService.getCurrentDateTime());
        return ResponseEntity.ok(response);
    }

    // GET /api/info - Application information
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Spring Boot REST API");
        response.put("version", "1.0.0");
        response.put("java", System.getProperty("java.version"));
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
} 