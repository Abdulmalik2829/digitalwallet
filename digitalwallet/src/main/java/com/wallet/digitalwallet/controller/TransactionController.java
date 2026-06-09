package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.entity.Transaction;
import com.wallet.digitalwallet.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.wallet.digitalwallet.service.WalletService;
import com.wallet.digitalwallet.entity.Wallet;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final WalletService walletService;

    public TransactionController(TransactionService transactionService, WalletService walletService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(Authentication authentication) {

        String username = authentication.getName();

        Wallet wallet = walletService.getWalletByUsername(username);

        List<Transaction> history = transactionService.getTransactionByWallet(wallet.getId());

        return ResponseEntity.ok(history);
    }
}
