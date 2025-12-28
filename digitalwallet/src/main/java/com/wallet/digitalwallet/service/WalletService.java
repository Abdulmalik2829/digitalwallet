package com.wallet.digitalwallet.service;

import com.wallet.digitalwallet.entity.Wallet;
import com.wallet.digitalwallet.entity.Transaction;
import com.wallet.digitalwallet.enums.TransactionStatus;
import com.wallet.digitalwallet.enums.TransactionType;
import com.wallet.digitalwallet.repository.WalletRepository;
import com.wallet.digitalwallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Wallet deposit(Long walletId, BigDecimal amount){
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Error: Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(amount));

        Transaction transaction = new Transaction(
                amount,
                TransactionType.CREDIT,
                TransactionStatus.COMPLETED,
                wallet
        );
        transactionRepository.save(transaction);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet withdraw(Long walletId, BigDecimal amount){
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Error: wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Error: Insufficient Balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        Transaction transaction = new Transaction(
                amount,
                TransactionType.DEBIT,
                TransactionStatus.COMPLETED,
                wallet
        );
        transactionRepository.save(transaction);

        return walletRepository.save(wallet);
    }

    @Transactional
    public void transfer(Long senderWalletId, Long receiverWalletId, BigDecimal amount){
        withdraw(senderWalletId, amount);
        deposit(receiverWalletId, amount);
    }
}

