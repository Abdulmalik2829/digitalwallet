package com.wallet.digitalwallet.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    private String currency = "NGN";

    @Version
    private Long version;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;


    public Wallet(){

    }

    public Wallet(AppUser user, String currency){
        this.user = user;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
    }

    //Getters and Setters
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions){
        this.transactions = transactions;
    }

    public Long getId(){
        return id;
    }

    public Long getVersion(){
        return version;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public AppUser getUser(){
        return user;
    }
}
