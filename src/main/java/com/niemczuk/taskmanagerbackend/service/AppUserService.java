package com.niemczuk.taskmanagerbackend.service;

import com.niemczuk.taskmanagerbackend.model.AppUser;
import com.niemczuk.taskmanagerbackend.repository.AppUserRepository;
import com.niemczuk.taskmanagerbackend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            log.error("User is not present in database");
            throw new UsernameNotFoundException("User is not present in database");
        } else {
            log.info("User {} found in database", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    public void saveUser(AppUser appUser) {
        appUser.setRole(roleRepository.findByName("ROLE_USER"));
        appUserRepository.save(appUser);
    }

    public AppUser getUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }
}
