package com.example.fifa_world_cup_2026.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "{register.username.notblank}")
    @Size(min = 3, max = 50, message = "{register.username.size}")
    private String username;

    @NotBlank(message = "{register.email.notblank}")
    @Email(message = "{register.email.invalid}")
    private String email;

    @NotBlank(message = "{register.password.notblank}")
    @Size(min = 6, max = 100, message = "{register.password.size}")
    private String password;

    @NotBlank(message = "{register.confirmPassword.notblank}")
    private String confirmPassword;

}
