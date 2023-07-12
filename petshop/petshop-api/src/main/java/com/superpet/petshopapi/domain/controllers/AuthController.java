package com.superpet.petshopapi.domain.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superpet.petshopapi.domain.dtos.LoginPayloadDto;
import com.superpet.petshopapi.domain.entities.User;
import com.superpet.petshopapi.domain.repositories.UserRepository;


@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    
    @PostMapping("login")
    public Map<String, Object> login(@RequestBody LoginPayloadDto dto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        authenticationManager.authenticate(authentication);
        return new HashMap<String, Object>() {
            {
                put("token", "123456");
            }
        };
    }

    @GetMapping("activate/{userID}/{code}")
    public Map<String, String> activateUser(@PathVariable String code, @PathVariable Long userID) {
        Optional<User> optUser = userRepository.findById(userID);
        if (optUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optUser.get();
        user.setEnabled(true);
        userRepository.save(user);
        return new HashMap<>() {
            {
                put("message", " User activated");
            }
        };

    }
}
