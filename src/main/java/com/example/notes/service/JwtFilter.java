package com.example.notes.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtservice;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    		FilterChain filterChain)  throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (auth != null && auth.startsWith("Bearer ")) {
            token = auth.substring(7);
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie ck : cookies) {
                    if ("jwt".equals(ck.getName())) {
                        token = ck.getValue();   
                        break;
                    }
                }
            }
        }

        // No token at all → just continue
        if (token != null) {
            try {
                email = jwtservice.extractEmail(token);
            } catch (io.jsonwebtoken.JwtException e) {
                email = null;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = context.getBean(MyUsersService.class).loadUserByUsername(email);

            if (jwtservice.validateToken(token, user)) {
                // Set authentication in security context
                UsernamePasswordAuthenticationToken authtoken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);

                // Sliding expiration: refresh token if less than 5 minutes left
                Date expiry = jwtservice.extractExpiration(token);
                long ms = expiry.getTime() - System.currentTimeMillis();

                if (ms < 5 * 60 * 1000) { // less than 5 min remaining
                    String newtoken = jwtservice.generateToken(user.getUsername());

                    ResponseCookie cookie = ResponseCookie.from("jwt", newtoken)
                            .httpOnly(true)
                            .path("/")          
                            .maxAge(30 * 60)  
                            .build();

                    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
