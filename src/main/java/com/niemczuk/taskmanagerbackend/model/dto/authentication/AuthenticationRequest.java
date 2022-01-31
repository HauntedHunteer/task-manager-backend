package com.niemczuk.taskmanagerbackend.model.dto.authentication;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, max = 25, message = "Username must have 4 - 25 characters")
    String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Username must have at least 8 characters")
    String password;
}
