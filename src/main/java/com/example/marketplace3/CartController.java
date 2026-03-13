package com.example.marketplace3;

import com.example.marketplace3.model.*;
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

        model.addAttribute("cartItems", items);

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

    @PostMapping("/increase/{id}")
    public String increase(@PathVariable Long id) {

        CartItem item = cartRepo.findById(id).orElseThrow();
        item.setQuantity(item.getQuantity() + 1);
        cartRepo.save(item);

        return "redirect:/cart";
    }

    @PostMapping("/decrease/{id}")
    public String decrease(@PathVariable Long id) {

        CartItem item = cartRepo.findById(id).orElseThrow();

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartRepo.save(item);
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {

        cartRepo.deleteById(id);

        return "redirect:/cart";
    }
}