package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.entity.Transaction;
import com.wallet.digitalwallet.entity.Wallet;
import com.wallet.digitalwallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/wallets")
public class WalletController{

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(Authentication authentication) {

        String username = authentication.getName();

        Wallet wallet = walletService.getWalletByUsername(username);

        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(Authentication authentication, @RequestParam BigDecimal amount){

        String username = authentication.getName();

        Wallet wallet = walletService.getWalletByUsername(username);

        Transaction receipt = walletService.deposit(wallet.getId(), amount);

        return ResponseEntity.ok(receipt);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(Authentication authentication, @RequestParam BigDecimal amount){

        String username = authentication.getName();

        Wallet wallet = walletService.getWalletByUsername(username);

        Transaction receipt = walletService.withdraw(wallet.getId(), amount);

        return ResponseEntity.ok(receipt);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(Authentication authentication, @RequestParam Long receiverId, @RequestParam BigDecimal amount){

        String senderUsername = authentication.getName();

        walletService.transferByUsername(senderUsername, receiverId, amount);

        return ResponseEntity.ok("Transfer Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWallet(@PathVariable Long id){
        Wallet wallet = walletService.getWalletById(id);
        return ResponseEntity.ok(wallet);
    }
}
