package com.example.marketplace3;

import com.example.marketplace3.model.User;
import com.example.marketplace3.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           @RequestParam(required = false) String admin) {

        if (admin != null) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }

        userRepository.save(user);

        return "redirect:/";
    }
}