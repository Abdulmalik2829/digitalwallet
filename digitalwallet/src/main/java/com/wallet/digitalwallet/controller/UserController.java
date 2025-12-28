package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.entity.AppUser;
import com.wallet.digitalwallet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser user){
        AppUser createdUser = userService.registerNewUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserProfile(@PathVariable Long Id){
        AppUser user = userService.getUserById(Id);
        return ResponseEntity.ok(user);
    }
}
