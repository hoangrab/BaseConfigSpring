package com.hocvui.controllers;

import com.hocvui.exception.ResourceExistException;
import com.hocvui.repositories.UserRepo;
import com.hocvui.request.LoginRequest;
import com.hocvui.request.RegisterRequest;
import com.hocvui.response.ErrorRespon;
import com.hocvui.response.SuccessRespon;
import com.hocvui.services.UserService;
import jakarta.validation.Valid;
import org.hibernate.query.ReturnableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;
    @Autowired
    public UserController(UserService userService, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.userService = userService;

    }

    @PostMapping("auth/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest);
            return ResponseEntity.ok().body(new SuccessRespon(token));
        }catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorRespon<>(e.getMessage()));
        }
    }

    @PostMapping("auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try{
            if(userRepo.existsByEmail(registerRequest.getEmail())) {
                throw new ResourceExistException(registerRequest.getEmail(),HttpStatus.CONFLICT);
            }
            boolean check = userService.register(registerRequest);
            return check ? ResponseEntity.status(HttpStatus.CREATED).body(new SuccessRespon<>("User registered successfully",null))
                         : ResponseEntity.badRequest().build();
        }catch (ResourceExistException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorRespon<>(e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorRespon<>(e.getMessage()));
        }
    }

}
