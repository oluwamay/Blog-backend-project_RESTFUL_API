package com.invogue_fashionblog.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    @NotNull(message = "This field cannot be empty")
    private String firstName;

    @NotNull(message = "This field cannot be empty")
    private String lastName;

    @NotNull(message ="This field cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message ="This field cannot be empty")
    @Length(min=5, message="password cannot be less than 5 characters")
    private String password;
}
