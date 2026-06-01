package com.ecommerce.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.auth.model.User;

public interface UserRepository
extends JpaRepository<User,Integer>{

    User findByUsernameAndPassword(
        String username,
        String password
    );
}