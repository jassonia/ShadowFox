package com.ecommerce.auth.service;
import com.ecommerce.auth.util.JwtUtil;
import com.ecommerce.auth.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    // Temporary Database
    private List<User> users = new ArrayList<>();


    // Register User
    public String registerUser(User user) {

        // Check if email already exists
        for (User existingUser : users) {

            if (
    existingUser.getUsername().equals(user.getUsername())) {

                return "User already exists!";
            }
        }

        users.add(user);

        return "Registration Successful!";
    }


    // Login User
    public String loginUser(String name, String password) {

        for (User user : users) {

            if (user.getUsername().equals(name)
                && user.getPassword().equals(password)) {

                return JwtUtil.generateToken(name);
            }
        }

        return "Invalid Email or Password!";
    }


    // Get All Users
    public List<User> getAllUsers() {

        return users;
    }
}
