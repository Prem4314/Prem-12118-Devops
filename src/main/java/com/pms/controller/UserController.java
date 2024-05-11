package com.pms.controller;

import com.pms.bean.User;
import com.pms.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDAO userRepo;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userRepo.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        userRepo.deleteUser(id);
        return true;
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setUserMobno(updatedUser.getUserMobno());
            return userRepo.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
