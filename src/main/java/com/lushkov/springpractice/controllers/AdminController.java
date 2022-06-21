package com.lushkov.springpractice.controllers;

import com.lushkov.springpractice.constants.Constant;
import com.lushkov.springpractice.dto.UserDto;
import com.lushkov.springpractice.models.User;
import com.lushkov.springpractice.services.RoleService;
import com.lushkov.springpractice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("userName", principal.getName());
        return Constant.ADMIN_HOME;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("userId") long userId) {
        userService.delete(userService.read(userId));
        return Constant.ADMIN_REDIRECT_HOME;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("mode", Constant.MODE_ADD);
        model.addAttribute("userDto", roleService.findAll());
        return Constant.ADMIN_ADD_OR_EDIT;
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("userDto") @Validated UserDto userDto,
                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Constant.ADMIN_REDIRECT_ADD;
        } else {
            User user = userDto.toUser(roleService.read(userDto.getRoleId()));
            userService.create(user);
            return Constant.ADMIN_REDIRECT_HOME;
        }
    }

    @GetMapping("/edit")
    public String edit(HttpServletRequest request, Model model,
                       @RequestParam("userId") long userId) {
        request.getSession().setAttribute("user", userService.read(userId));
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("mode", Constant.MODE_EDIT);
        model.addAttribute("userDto", roleService.findAll());
        return Constant.ADMIN_ADD_OR_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("userDto") @Validated UserDto userDto,
                       BindingResult bindingResult, HttpServletRequest request) {
        User user;
        if (bindingResult.hasErrors() && !bindingResult.hasFieldErrors("password")) {
            return Constant.ADMIN_REDIRECT_EDIT;
        } else {
            if (userDto.getPassword().equals("")) {
                userDto.setPassword(((User) request.getSession()
                        .getAttribute("user")).getPassword());
            }
            user = userDto.toUser(roleService.read(userDto.getRoleId()));
            user.setUserId(userService.findByLogin(userDto.getLogin()).getUserId());
            userService.update(user);
            return Constant.ADMIN_REDIRECT_HOME;
        }
    }
}
