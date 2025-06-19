
package vs.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vs.java.service.DateTimeService;
import vs.java.service.GreetingService;

@Controller
public class HomeController {

    private final GreetingService greetingService;
    private final DateTimeService dateTimeService;

    public HomeController(GreetingService greetingService, DateTimeService dateTimeService) {
        this.greetingService = greetingService;
        this.dateTimeService = dateTimeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to My Spring Boot Web App!");
        return "index";
    }

    // @GetMapping("/greeting")
    // public String greeting(@RequestParam(name = "name" , required = false , defaultValue = "World") String name, Model model) {
    //     model.addAttribute("greeting", greetingService.getGreeting(name));
    //     return "greeting";
    // }
    @PostMapping("/greeting")
    public String greeting(@RequestParam String name, Model model) {
        model.addAttribute("greeting", greetingService.getGreeting(name));
        return "greeting";
    }

    @GetMapping("/datetime")
    public String dateTime(Model model) {
        model.addAttribute("currentDateTime", dateTimeService.getCurrentDateTime());
        return "datetime";
    }
}