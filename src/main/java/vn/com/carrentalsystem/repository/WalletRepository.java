package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.Wallet;
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
