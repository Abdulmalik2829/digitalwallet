package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.dto.UserResponse;
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
    public ResponseEntity<UserResponse> register(@RequestBody AppUser user){
        AppUser createdUser = userService.registerNewUser(user);

        UserResponse response = new UserResponse(
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getfirstName(),
                createdUser.getlastName()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserProfile(@PathVariable Long id){
        AppUser user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
