package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.entity.Transaction;
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
    public ResponseEntity<Transaction> deposit(@PathVariable Long id, @RequestParam BigDecimal amount){
        Transaction receipt = walletService.deposit(id, amount);
        return ResponseEntity.ok(receipt);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Transaction> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount){
        Transaction receipt = walletService.withdraw(id, amount);
        return ResponseEntity.ok(receipt);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam BigDecimal amount){
        walletService.transfer(senderId, receiverId, amount);
        return ResponseEntity.ok("Transfer Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWallet(@PathVariable Long id){
        Wallet wallet = walletService.getWalletById(id);
        return ResponseEntity.ok(wallet);
    }
}
