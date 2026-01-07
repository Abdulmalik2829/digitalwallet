package com.wallet.digitalwallet.controller;

import com.wallet.digitalwallet.entity.Transaction;
import com.wallet.digitalwallet.service.TransactionService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long walletId){
        List<Transaction> history = transactionService.getTransactionByWallet(walletId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsHistory(@PathVariable Long id){
        List<Transaction> history = walletService.getWalletHistory(id);

        if (history.isEmpty()) {
            return ResponseEntity.noContent().build(); // Standard way to say "No history yet"
        }

        return ResponseEntity.ok(history);
    }
}
