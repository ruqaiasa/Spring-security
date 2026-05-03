package com.example.SpringSecExpl.controller;

import com.example.SpringSecExpl.model.Users;
import com.example.SpringSecExpl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

@RestController
public class UserController {

    @Autowired
    private UserService service;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);


    @PostMapping("/add")
    public Users registerUser(@RequestBody Users user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      return service.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        return service.verifyUser(user);
    }


}
