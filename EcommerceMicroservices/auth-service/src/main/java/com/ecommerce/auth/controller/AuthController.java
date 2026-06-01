package com.ecommerce.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.auth.model.User;
import com.ecommerce.auth.repository.UserRepository;

import org.springframework.http.ResponseEntity;

@RestController

@RequestMapping("/customers")

@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private UserRepository repository;

    /* REGISTER */

    @PostMapping("/register")

public ResponseEntity<?> registerUser(
    @RequestBody User user
){

    User existingUser =
    repository.findByUsernameAndPassword(

        user.getUsername(),

        user.getPassword()
    );

    /* SAME USERNAME + PASSWORD */

    if(existingUser != null){

        return ResponseEntity
        .status(409)
        .body("User already exists");
    }

    /* SAVE USER */

    User savedUser =
    repository.save(user);

    return ResponseEntity.ok(savedUser);
}

    /* LOGIN */

    @PostMapping("/login")

public ResponseEntity<?> loginUser(
    @RequestBody User user
){

    User existingUser =
    repository.findByUsernameAndPassword(

        user.getUsername(),

        user.getPassword()
    );

    if(existingUser == null){

        return ResponseEntity
        .status(401)
        .body("Login Failed");
    }

    return ResponseEntity.ok(existingUser);
}
}