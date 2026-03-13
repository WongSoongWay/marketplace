package com.example.marketplace3;

import com.example.marketplace3.model.OrderHistory;
import com.example.marketplace3.model.Product;
import com.example.marketplace3.repository.OrderHistoryRepository;
import com.example.marketplace3.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderRepo;

    public AdminController(ProductRepository productRepository, OrderHistoryRepository orderRepo) {
        this.productRepository = productRepository;
        this.orderRepo = orderRepo;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", productRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin/add-product")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin";
    }
    @GetMapping("/admin/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        Product product = productRepository.findById(id).orElseThrow();

        model.addAttribute("product", product);

        return "edit-product";
    }

    @PostMapping("/admin/update")
    public String updateProduct(@ModelAttribute Product product) {

        System.out.println("ID received: " + product.getId());


        productRepository.save(product);

        return "redirect:/admin";
    }

    @PostMapping("/admin/toggle-visibility/{id}")
    public String toggleVisibility(@PathVariable Long id) {

        Product product = productRepository.findById(id).orElseThrow();

        product.setVisible(!product.isVisible());

        productRepository.save(product);

        return "redirect:/admin";
    }
    @GetMapping("/orders")
    public String adminOrders(Model model) {
        List<OrderHistory> orders = orderRepo.findAll();
        model.addAttribute("orders", orders);
        return "orders"; // create a Thymeleaf template for this
    }

    // Update status
    @PostMapping("/admin/orders/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        OrderHistory order = orderRepo.findById(id).orElseThrow();
        order.setStatus(status);
        orderRepo.save(order);
        return "redirect:/orders";
    }
}