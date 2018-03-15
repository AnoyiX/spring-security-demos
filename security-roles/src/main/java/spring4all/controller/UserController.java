package spring4all.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring4all.entity.UserEntity;
import spring4all.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/sales")
    public String user(@AuthenticationPrincipal Principal principal, Model model){
        UserEntity user = userService.getByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/sales";
    }

    @GetMapping("/order")
    public String order(@AuthenticationPrincipal Principal principal, Model model){
        UserEntity user = userService.getByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/order";
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal Principal principal, Model model){
        UserEntity user = userService.getByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/admin";
    }

}
