package com.example.marketplace3;

import com.example.marketplace3.model.CartItem;
import com.example.marketplace3.model.CartItemView;
import com.example.marketplace3.model.Product;
import com.example.marketplace3.model.User;
import com.example.marketplace3.repository.CartItemRepository;
import com.example.marketplace3.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public CartController(CartItemRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        List<CartItem> items = cartRepo.findByUserId(user.getId());

        // Build view objects with product info
        List<CartItemView> cartItems = items.stream().map(item -> {
            Product product = productRepo.findById(item.getProductId()).orElseThrow();
            return new CartItemView(item.getId(), product, item.getQuantity());
        }).toList();

        double total = cartItems.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addProduct(@PathVariable Long productId,
                             @RequestParam int quantity,
                             HttpSession session) {

        User user = (User) session.getAttribute("user");

        CartItem item = cartRepo
                .findByUserIdAndProductId(user.getId(), productId)
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem(user.getId(), productId, quantity);
        }

        cartRepo.save(item);

        return "redirect:/cart";
    }

    // --- NEW: Update cart item ---
    @PostMapping("/update/{id}")
    public String updateCartItem(@PathVariable Long id,
                                 @RequestParam int quantity,
                                 @RequestParam String action) {

        CartItem item = cartRepo.findById(id).orElseThrow();

        switch (action) {
            case "increase" -> item.setQuantity(item.getQuantity() + 1);
            case "decrease" -> {
                if (item.getQuantity() > 1) item.setQuantity(item.getQuantity() - 1);
            }
            case "update" -> {
                if (quantity > 0) item.setQuantity(quantity);
            }
            case "remove" -> {
                cartRepo.delete(item);
                return "redirect:/cart";
            }
        }

        cartRepo.save(item);
        return "redirect:/cart";
    }
}