package com.example.marketplace3;

import com.example.marketplace3.model.OrderHistory;
import com.example.marketplace3.model.Product;
import com.example.marketplace3.repository.OrderHistoryRepository;
import com.example.marketplace3.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        // Get all distinct order IDs with their current status (assume all items in an order share same status)
        List<OrderHistory> orders = orderRepo.findAll();

        // Map orderId -> status (pick status of first item in order)
        Map<Long, String> orderStatusMap = new LinkedHashMap<>();
        orders.stream()
                .collect(Collectors.groupingBy(OrderHistory::getOrderId))
                .forEach((orderId, items) -> orderStatusMap.put(orderId, items.get(0).getStatus()));

        model.addAttribute("orders", orderStatusMap);

        return "orders"; // new Thymeleaf template
    }

    @PostMapping("/orders/update-status/{orderId}")
    public String updateOrderStatus(@PathVariable Long orderId,
                                    @RequestParam String status) {
        // Update status for all items in this order
        List<OrderHistory> orderItems = orderRepo.findByOrderId(orderId);
        for (OrderHistory item : orderItems) {
            item.setStatus(status);
            orderRepo.save(item);
        }

        return "redirect:/orders";
    }
}