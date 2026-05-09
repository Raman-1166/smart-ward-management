package com.ward.system.controller.mvc;

import com.ward.system.exception.DuplicateEmailException;
import com.ward.system.model.Role;
import com.ward.system.model.User;
import com.ward.system.model.Ward;
import com.ward.system.service.UserService;
import com.ward.system.service.WardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private WardService wardService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("wards", wardService.getAllWardsList());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user,
                               BindingResult bindingResult,
                               @RequestParam(required = false) Long wardId,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("wards", wardService.getAllWardsList());
            return "register";
        }

        // Resolve the full Ward entity from the submitted ward ID to avoid partial-entity issues
        if (wardId != null) {
            Ward ward = wardService.getWardById(wardId);
            user.setWard(ward);
        } else {
            user.setWard(null);
        }

        // Registration is always CITIZEN — admin accounts are pre-seeded only
        user.setRole(Role.CITIZEN);

        try {
            userService.register(user);
        } catch (DuplicateEmailException e) {
            model.addAttribute("emailError", e.getMessage());
            model.addAttribute("wards", wardService.getAllWardsList());
            return "register";
        }

        return "redirect:/login?registered";
    }
}
