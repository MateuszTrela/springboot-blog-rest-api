package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpDto {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
