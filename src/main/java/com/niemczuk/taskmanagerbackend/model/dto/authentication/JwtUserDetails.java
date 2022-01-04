package com.niemczuk.taskmanagerbackend.model.dto.authentication;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtUserDetails {

    String username;
    Collection<SimpleGrantedAuthority> authorities;
}
