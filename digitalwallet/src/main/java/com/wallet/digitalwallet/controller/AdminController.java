package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.entity.Wallet;
import com.wallet.digitalwallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final WalletService walletService;

    public AdminController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Wallet>> getAllSystemWallets() {

        List<Wallet> allWallets = walletService.getAllWalletsForAdmin();
        return ResponseEntity.ok(allWallets);
    }
}