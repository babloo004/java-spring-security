package com.avinash.SpringSecurity.controller;

import com.avinash.SpringSecurity.model.User;
import com.avinash.SpringSecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    //Service object
    @Autowired
    private UserService service;

    //Bcrypt object
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @GetMapping("/")
    public String home(HttpServletRequest request){
        return ("Home page : SID ( "+request.getSession().getId()+" )");
    }

    @PostMapping("/user")
    public List<User> addUser(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return service.addUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return service.getUsers();
    }
}
