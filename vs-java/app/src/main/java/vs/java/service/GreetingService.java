
package vs.java.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String getGreeting(String name) {
        if(name != null && !name.isEmpty()) {
            return "Hello " + name + "! Greeting from the GreetingService.";
        }
        return "Hello World! Greeting from the GreetingService.";
    }
}