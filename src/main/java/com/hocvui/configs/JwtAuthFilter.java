package com.hocvui.configs;

import com.hocvui.model.CustomUserDetail;
import com.hocvui.repositories.UserRepo;
import com.hocvui.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final UserDetailsService userDetailsService;
    public JwtAuthFilter(JwtService jwtService, UserRepo userRepo, UserDetailsService
                         userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepo = userRepo;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        try{
            if(accessToken != null && jwtService.isValidToken(accessToken)) {
                int userId = jwtService.getIdFromToken(accessToken);

                UserDetails userDetails1 = userRepo.findById(userId)
                        .map(user -> userDetailsService.loadUserByUsername(user.getEmail()))
                        .orElseThrow(() -> new RuntimeException("Not found user id: "+userId));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails1,
                        null,userDetails1.getAuthorities());
                auth.setDetails(userDetails1);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (ExpiredJwtException e) {
            // Handle expired token (both access and refresh)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            return;
        } catch (JwtException e) {
            // Handle other JWT exceptions
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        } catch (Exception e) {
            // Handle other exceptions
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
