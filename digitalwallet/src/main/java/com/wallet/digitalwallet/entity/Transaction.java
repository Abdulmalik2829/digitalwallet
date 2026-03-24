package com.wallet.digitalwallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wallet.digitalwallet.enums.TransactionStatus;
import com.wallet.digitalwallet.enums.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    @JsonIgnoreProperties({"transactions", "user", "balance", "currency", "version"})
    private Wallet wallet;

    private String reference;

    private Long counterpartyWalletId;

    public Transaction(){
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(BigDecimal amount, TransactionType type, TransactionStatus status, Wallet wallet, String reference, Long counterpartyWalletId) {
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.wallet = wallet;
        this.timestamp = LocalDateTime.now();
        this.reference = reference;
        this.counterpartyWalletId = counterpartyWalletId;
    }

    public Long getId(){
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getCounterpartyWalletId() {
        return counterpartyWalletId;
    }

    public void setCounterpartyWalletId(Long counterpartyWalletId) {
        this.counterpartyWalletId = counterpartyWalletId;
    }
}
