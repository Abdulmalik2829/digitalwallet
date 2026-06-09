package com.wallet.digitalwallet.repository;

import com.wallet.digitalwallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{

    Optional<Wallet> findByUser_Username(String Username);

}
