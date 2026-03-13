package com.example.marketplace3;

import com.example.marketplace3.model.OrderHistory;
import com.example.marketplace3.model.OrderHistoryView;
import com.example.marketplace3.model.Product;
import com.example.marketplace3.model.User;
import com.example.marketplace3.repository.OrderHistoryRepository;
import com.example.marketplace3.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class OrderHistoryController {

    private final OrderHistoryRepository orderRepo;
    private final ProductRepository productRepo;

    public OrderHistoryController(OrderHistoryRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @GetMapping("/order-history")
    public String viewOrderHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // get all orders for the user
        List<OrderHistory> orders = orderRepo.findByUserIdOrderByOrderIdDesc(user.getId());

        // map to OrderHistoryView so we have the product object
        List<OrderHistoryView> views = orders.stream().map(o -> {
            Product product = productRepo.findById(o.getProductId()).orElseThrow();
            return new OrderHistoryView(
                    o.getOrderId(),
                    product,           // keep the product object
                    o.getQuantity(),
                    o.getPrice(),      // price at checkout
                    o.getStatus()
            );
        }).toList();

        // group by orderId
        Map<Long, List<OrderHistoryView>> groupedOrders = views.stream()
                .collect(Collectors.groupingBy(OrderHistoryView::getOrderId, LinkedHashMap::new, Collectors.toList()));

        // compute total price per order
        Map<Long, Double> orderTotals = new LinkedHashMap<>();
        groupedOrders.forEach((orderId, items) -> {
            double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
            orderTotals.put(orderId, total);
        });

        model.addAttribute("groupedOrders", groupedOrders);
        model.addAttribute("orderTotals", orderTotals);

        return "order-history";
    }
}