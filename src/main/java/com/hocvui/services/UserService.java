package com.hocvui.services;

import com.hocvui.DTO.UserDTO;
import com.hocvui.entities.Role;
import com.hocvui.entities.User;
import com.hocvui.entities.UserRole;
import com.hocvui.model.CustomUserDetail;
import com.hocvui.repositories.RoleRepo;
import com.hocvui.repositories.UserRepo;
import com.hocvui.request.LoginRequest;
import com.hocvui.request.RegisterRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, UserRepo userRepo,
                       JwtService jwtService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<User> save(UserDTO userDTO) {
        Optional<User> user = Optional.of(userRepo.save(new User(userDTO.getGmail(), userDTO.getPassword())));
        return user;
    }


    public String login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            CustomUserDetail customUserDetail = (CustomUserDetail) userDetails;
            return jwtService.generateToken(customUserDetail);
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("UserName or password invalid",e);
        }
    }

    public boolean register(RegisterRequest registerRequest) {
        if(userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Gmail already exists");
        }

        User user = new User(registerRequest.getUsername(),registerRequest.getEmail(),passwordEncoder.encode(registerRequest.getPassword()));
        addDefaultRoleToUser(user);
        user.setEnabled(true);
        userRepo.save(user);
        return true;
    }

    public void addDefaultRoleToUser(User user) {
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        Optional<Role> role = roleService.findByName("USER");
        if(role.isEmpty()) {
            Role r = new Role("USER");
            roleService.save(r);
            userRole.setRole(r);
        }
        else userRole.setRole(role.get());
        userRole.setUser(user);
        userRoles.add(userRole);
        user.setRoles(userRoles);
    }

}
