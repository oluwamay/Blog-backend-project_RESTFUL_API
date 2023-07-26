package com.invogue_fashionblog.dto.requests;

import com.invogue_fashionblog.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    @NotBlank(message = "This field cannot be empty")
    private String firstName;

    @NotBlank(message = "This field cannot be empty")
    private String lastName;

    @NotBlank(message ="This field cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    @Length(min = 8, max = 24, message = "Password cannot be less than 8 or more than 24 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%&]).*$",
            message = "Password must contain at least one letter, one digit, and one special character")
    private String password;

    @NotNull(message = "This field cannot be empty")
    private Roles role;
}
