package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.carrentalsystem.entities.PaymentTransaction;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
