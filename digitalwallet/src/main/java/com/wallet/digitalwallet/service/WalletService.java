package com.wallet.digitalwallet.service;

import com.wallet.digitalwallet.entity.Wallet;
import com.wallet.digitalwallet.entity.Transaction;
import com.wallet.digitalwallet.enums.TransactionStatus;
import com.wallet.digitalwallet.enums.TransactionType;
import com.wallet.digitalwallet.exception.InvalidOperationException;
import com.wallet.digitalwallet.repository.WalletRepository;
import com.wallet.digitalwallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction deposit(Long walletId, BigDecimal amount){
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new InvalidOperationException("Error: Wallet not found"));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidOperationException("Error: Amount must be greater than zero");
        }

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        String reference = UUID.randomUUID().toString();

        Transaction transaction = new Transaction(
                amount,
                TransactionType.CREDIT,
                TransactionStatus.COMPLETED,
                wallet,
                reference,
                null
        );

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long walletId, BigDecimal amount){
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new InvalidOperationException("Error: wallet not found"));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidOperationException("Error: Amount must be greater than zero");
        }

        if (wallet.getBalance().compareTo(amount) < 0){
            throw new InvalidOperationException("Error: Insufficient Balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        String reference = UUID.randomUUID().toString();

        Transaction transaction = new Transaction(
                amount,
                TransactionType.DEBIT,
                TransactionStatus.COMPLETED,
                wallet,
                reference,
                null
        );

        walletRepository.save(wallet);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void transfer(Long senderWalletId, Long receiverWalletId, BigDecimal amount){

        if (senderWalletId == null || receiverWalletId == null){
            throw new InvalidOperationException("Sender and receiver wallet IDs are required");
        }

        if (senderWalletId.equals(receiverWalletId)){
            throw new InvalidOperationException("Sender wallet and receiver wallet must be different");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidOperationException("Transfer amount must be greater than zero");
        }

        Wallet senderWallet = walletRepository.findById(senderWalletId)
                        .orElseThrow(() -> new InvalidOperationException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findById(receiverWalletId)
                        .orElseThrow(() -> new InvalidOperationException("Receiver wallet not found"));

        if (senderWallet.getBalance().compareTo(amount) < 0){
            throw new InvalidOperationException("Insufficient Balance");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        String reference = UUID.randomUUID().toString();

        Transaction transferTx = new Transaction(
                amount,
                TransactionType.DEBIT,
                TransactionStatus.COMPLETED,
                senderWallet,
                reference,
                null
        );

        transactionRepository.save(transferTx);
    }

    @Transactional
    public Wallet getWalletById(Long id){
        return walletRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Wallet not found with id" + id));
    }

    @Transactional
    public List<Transaction> getWalletHistory(Long id){
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Wallet not found with id" + id));
        return wallet.getTransactions();

    }

    @Transactional(readOnly = true)
    public Wallet getWalletByUsername(String username){

        if (username == null || username.trim().isEmpty()){
            throw new InvalidOperationException("Username cannot be empty");
        }

        return walletRepository.findByUser_Username(username)
                .orElseThrow(() -> new InvalidOperationException("Error: Wallet not found for user: " + username));
    }

    @Transactional
    public void transferByUsername(String senderUsername, Long receiverWalletId, BigDecimal amount){
        Wallet senderWallet = getWalletByUsername(senderUsername);
        transfer(senderWallet.getId(), receiverWalletId, amount);
    }

    @Transactional(readOnly = true)
    public List<Wallet> getAllWalletsForAdmin() {
        return walletRepository.findAll();
    }
}

