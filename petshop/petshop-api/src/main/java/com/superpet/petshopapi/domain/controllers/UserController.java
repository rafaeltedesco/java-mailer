package com.superpet.petshopapi.domain.controllers;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superpet.petshopapi.domain.entities.User;
import com.superpet.petshopapi.domain.repositories.UserRepository;
import com.superpet.petshopapi.domain.services.MailService;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MailService mailService;

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
       User newUser = userRepository.save(user);
        String code = encoder.encode(newUser.getEmail());
        String urlCode = "http://localhost:8080/activate/" + newUser.getId() + "/" + code;
        try {
            mailService.sendMail(newUser.getEmail(), "Your activation code", String.format("Click here to activate your account <a href=\"%s\">%s</a>", urlCode, urlCode));
        } catch(Exception ex) {
            throw new RuntimeException(ex.getCause());
        }
       return newUser;
    }
    
}
