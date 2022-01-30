package com.niemczuk.taskmanagerbackend.service;

import com.niemczuk.taskmanagerbackend.exception.classes.UsernameTakenException;
import com.niemczuk.taskmanagerbackend.jwt.JwtUtility;
import com.niemczuk.taskmanagerbackend.model.AppUser;
import com.niemczuk.taskmanagerbackend.model.dto.authentication.AuthenticationRequest;
import com.niemczuk.taskmanagerbackend.model.dto.authentication.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        UserDetails userDetails = appUserService.loadUserByUsername(authRequest.getUsername());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = appUserService.getUserByUsername(authRequest.getUsername());

        String jwtToken = JwtUtility.generateJwtToken(userDetails);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .username(userDetails.getUsername())
                .roleName(appUser.getRole().getName())
                .build();
    }

    public void register(AuthenticationRequest authRequest) {
        if (appUserService.existsByUsername(authRequest.getUsername())) {
            throw new UsernameTakenException("Username is already taken");
        }

        AppUser userToSave = AppUser.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .build();

        appUserService.saveUser(userToSave);
    }
}
