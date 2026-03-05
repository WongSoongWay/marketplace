package com.example.marketplace3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {



    @GetMapping
    public String viewOrderHistory() {
        return "orderHistory";  // checkout.html
    }
}
