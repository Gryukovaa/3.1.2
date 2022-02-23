package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("listUsers", users);
        return "users";
    }

    // CREATE
    @PostMapping(value = "/create")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping( "/save")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "save";
    }

    // UPDATE
    @GetMapping(value = "/edit/{id}")
    public String getForEditUser(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.getUser(id);
        model.addAttribute("editedUser",user);
        return "edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        User newUser =  userService.getUser(id);
        newUser.setEmail(user.getEmail());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        userService.saveUser(newUser);
        return "redirect:/admin/users";
    }

    // DELETE
    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
