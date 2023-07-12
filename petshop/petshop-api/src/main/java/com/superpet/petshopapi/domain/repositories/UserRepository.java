package com.superpet.petshopapi.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.superpet.petshopapi.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    public UserDetails findByEmail(String email);
}
