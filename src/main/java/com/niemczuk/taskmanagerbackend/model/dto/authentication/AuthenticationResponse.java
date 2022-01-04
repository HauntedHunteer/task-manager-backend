package com.niemczuk.taskmanagerbackend.model.dto.authentication;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    String jwtToken;
    String username;
    String userId;
    String roleName;
}
