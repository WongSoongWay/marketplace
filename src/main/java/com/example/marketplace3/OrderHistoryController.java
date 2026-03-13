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

        List<OrderHistory> orders = orderRepo.findByUserIdOrderByOrderIdDesc(user.getId());

        // Build view objects
        List<OrderHistoryView> views = orders.stream().map(o -> {
            Product p = productRepo.findById(o.getProductId()).orElseThrow();
            return new OrderHistoryView(o.getOrderId(), p, o.getQuantity(), o.getPrice(), o.getStatus());
        }).toList();

        // Group by orderId
        Map<Long, List<OrderHistoryView>> grouped = views.stream()
                .collect(Collectors.groupingBy(OrderHistoryView::getOrderId, LinkedHashMap::new, Collectors.toList()));

        model.addAttribute("groupedOrders", grouped);

        return "order-history";
    }
}