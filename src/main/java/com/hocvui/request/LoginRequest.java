package com.hocvui.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @NotBlank(message = "UserName is required")
    @Email(message = "Invalid format email")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
