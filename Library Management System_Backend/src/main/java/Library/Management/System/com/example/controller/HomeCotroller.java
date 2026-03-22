package Library.Management.System.com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeCotroller {
    @GetMapping("/")
    public String home() {
        return "Welcome to the Library Management System!";
    }
}
