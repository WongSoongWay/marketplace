package com.example.marketplace3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.marketplace3.repository.ProductRepository;
import com.example.marketplace3.model.Product;

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
}