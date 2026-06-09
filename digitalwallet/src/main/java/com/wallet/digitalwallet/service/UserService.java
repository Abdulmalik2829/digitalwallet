package com.wallet.digitalwallet.service;

import org.mindrot.jbcrypt.BCrypt;
import com.wallet.digitalwallet.entity.AppUser;
import com.wallet.digitalwallet.entity.Role;
import com.wallet.digitalwallet.entity.Wallet;
import com.wallet.digitalwallet.repository.AppUserRepository;
import com.wallet.digitalwallet.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser registerNewUser(AppUser user){

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));

        user.setRoles(Collections.singleton(userRole));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        user.setWallet(wallet);

        return userRepository.save(user);
    }

    public AppUser getUserById(Long Id){
        return userRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Error: Id not found"));
    }
}
