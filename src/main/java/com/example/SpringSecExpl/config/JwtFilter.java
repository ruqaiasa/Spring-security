package com.example.SpringSecExpl.config;

import com.example.SpringSecExpl.service.CustomizedUserDetailsService;
import com.example.SpringSecExpl.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYXZpbiIsImlhdCI6MTc3NzYzNDAzMCwiZXhwIjoxNzc3NjM0MTM4fQ.o0rjZqKAQUALvdRstqXHkaZvN4s7FBjBb46FAEH-rBo
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader!=null && authHeader.startsWith("Bearer") ){
            token=authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }

        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = context.getBean(CustomizedUserDetailsService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authToken  =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
