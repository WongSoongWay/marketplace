package com.example.marketplace3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public String viewCart() {
        return "cart";  // cart.html
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";  // checkout.html
    }
}
