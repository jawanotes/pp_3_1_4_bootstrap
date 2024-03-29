package ru.kata.spring.boot_security.demo.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.LoadUserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
//@PreAuthorize("hasRole('USER')")
public class UnprivilegedController {
    private final LoadUserService loadUserService;

    public UnprivilegedController(@Qualifier(value = "loadUserServiceProvider")
                                  LoadUserService loadUserService) {
        this.loadUserService = loadUserService;
    }
    @GetMapping("")
    public String index() {
        return "redirect:/user/user";
    }
    @GetMapping("/user")
    public String userPage(Principal principal, ModelMap model) {
        User user = (User) loadUserService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user/user";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "/";
    }
}
