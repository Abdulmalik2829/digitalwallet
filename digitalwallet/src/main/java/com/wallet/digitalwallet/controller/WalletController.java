package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.entity.Wallet;
import com.wallet.digitalwallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
public class WalletController{

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Wallet> deposit(@PathVariable Long id, @RequestParam BigDecimal amount){
        Wallet updatedWallet = walletService.deposit(id, amount);
        return ResponseEntity.ok(updatedWallet);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Wallet> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount){
        Wallet updatedWallet = walletService.withdraw(id, amount);
        return ResponseEntity.ok(updatedWallet);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam BigDecimal amount){
        walletService.transfer(senderId, receiverId, amount);
        return ResponseEntity.ok("Transfer Successful");
    }
}
