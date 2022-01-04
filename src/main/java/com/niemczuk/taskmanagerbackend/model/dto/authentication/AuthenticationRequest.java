package com.niemczuk.taskmanagerbackend.model.dto.authentication;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    String username;
    String password;
}
