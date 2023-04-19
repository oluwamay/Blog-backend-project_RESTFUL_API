package com.invogue_fashionblog.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @Email(message = "Invalid password")
    private String email;

    @Length(min=5, message = "Password length cannot be less than 5")
    private String password;
}
