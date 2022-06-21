package com.lushkov.springpractice.controllers;

import com.lushkov.springpractice.constants.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public String user(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return Constant.USER_HELLO;
    }
}

