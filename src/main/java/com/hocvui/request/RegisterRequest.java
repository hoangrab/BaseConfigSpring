package com.hocvui.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RegisterRequest {
    @NotBlank(message = "UserName is required")
    private String username;
    @NotBlank
    @Email(message = "Invalid format email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6,message = "Length of password is invalid")
    private String password;
}
