package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.carrentalsystem.entities.PasswordResetToken;

import java.time.LocalDateTime;



public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);

    @Query("SELECT t FROM PasswordResetToken t WHERE t.user.userId = :id")
    PasswordResetToken findByUser (long id);

    void deleteByExpiryDateTimeBefore(LocalDateTime expiryDateTime);
}
