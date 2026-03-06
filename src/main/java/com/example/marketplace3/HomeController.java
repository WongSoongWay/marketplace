package com.example.marketplace3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.marketplace3.repository.ProductRepository;
import com.example.marketplace3.model.Product;
import org.springframework.ui.Model;


@Controller
public class HomeController {

    private final ProductRepository productRepository;

    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("products", productRepository.findAll());

        return "home";
    }
}