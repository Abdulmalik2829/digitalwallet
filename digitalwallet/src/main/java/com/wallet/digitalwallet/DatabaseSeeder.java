package com.wallet.digitalwallet;

import com.wallet.digitalwallet.entity.AppUser;
import com.wallet.digitalwallet.entity.Role;
import com.wallet.digitalwallet.entity.Wallet;
import com.wallet.digitalwallet.repository.AppUserRepository;
import com.wallet.digitalwallet.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DatabaseSeeder(RoleRepository roleRepository, AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {


        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));


        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));


        String adminUsername = "admin";

        if (!userRepository.existsByUsername(adminUsername)) {
            System.out.println(">> Creating secure system admin: " + adminUsername);

            AppUser admin = new AppUser();
            admin.setUsername(adminUsername);

            admin.setPassword(passwordEncoder.encode("Abdulmalik247@"));


            admin.setRoles(Collections.singleton(adminRole));


            Wallet wallet = new Wallet();
            wallet.setUser(admin);
            admin.setWallet(wallet);


            userRepository.save(admin);
        } else {

        }
    }
}