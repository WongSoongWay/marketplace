package com.example.marketplace3;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    @GetMapping("/login/user")
    public String user_login() {
        return "user_login";}

    @GetMapping("/login/admin")
    public String admin_login() {
        return "admin_login";}

    @GetMapping("/signup")
    public String signup() {
        return "signup";}




}


