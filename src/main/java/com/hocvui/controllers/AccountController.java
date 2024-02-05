package com.hocvui.controllers;

import com.hocvui.response.SuccessRespon;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class AccountController {
    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok().body(new SuccessRespon<>("Da truy cap thanh cong home user"));
    }


}
