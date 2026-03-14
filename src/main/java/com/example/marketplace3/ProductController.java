package com.example.marketplace3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.marketplace3.repository.ProductRepository;
import com.example.marketplace3.model.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/addProduct")
    public String addProduct() {

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(999);
        product.setImageUrl("https://files.refurbed.com/ii/apple-macbook-pro-2020-m1-1639050300.jpg");

        productRepository.save(product);

        return "redirect:/";
    }

    @GetMapping("/display/{id}")
    public String display(@PathVariable long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();

        model.addAttribute("product", product);

        return "productView";
    }
}