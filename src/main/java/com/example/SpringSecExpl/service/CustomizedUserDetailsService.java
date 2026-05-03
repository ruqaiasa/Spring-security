package com.example.SpringSecExpl.service;

import com.example.SpringSecExpl.model.UserPrincipal;
import com.example.SpringSecExpl.model.Users;
import com.example.SpringSecExpl.repo.UserRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomizedUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user != null) {
            return new UserPrincipal(user);
        }
        else
        {
            System.out.printf("User with username %s not found", username);
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

    }
}
