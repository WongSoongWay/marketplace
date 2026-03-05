package com.example.marketplace3;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @GetMapping("/")
    public String home() {
        return "first";}
}


