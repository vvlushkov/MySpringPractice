package com.lushkov.springpractice.controllers;

import com.lushkov.springpractice.constants.Constant;
import com.lushkov.springpractice.dto.UserDto;
import com.lushkov.springpractice.models.User;
import com.lushkov.springpractice.services.RoleService;
import com.lushkov.springpractice.services.UserService;
import com.lushkov.springpractice.utils.CaptchaValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
@RequestMapping("/")
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String login() {
        return Constant.AUTHORIZATION_LOGIN;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roleList", roleService.findAll());
        return Constant.AUTHORIZATION_REGISTRATION;
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userDto") @Validated UserDto userDto,
                               BindingResult bindingResult, HttpServletRequest request,
                               Model model) {
        String gRecaptchaResponse = request
                .getParameter("g-recaptcha-response");
        boolean verify = CaptchaValidation.verify(gRecaptchaResponse);
        if (!verify) {
            model.addAttribute("captcha", "invalid captcha");
            return Constant.AUTHORIZATION_REDIRECT_REGISTRATION;
        }
        if (bindingResult.hasErrors()) {
            return Constant.AUTHORIZATION_REDIRECT_REGISTRATION;
        } else {
            User user = userDto.toUser(roleService.read(userDto.getRoleId()));
            userService.create(user);
            return Constant.AUTHORIZATION_REDIRECT_LOGIN;
        }
    }

    @GetMapping("/default")
    public String defaultAfterLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String role = authorities.toArray()[0].toString();
        if (role.equals(Constant.ADMIN_ROLE)) {
            return Constant.AUTHORIZATION_REDIRECT_ADMIN;
        }
        return Constant.AUTHORIZATION_REDIRECT_USER;
    }
}

