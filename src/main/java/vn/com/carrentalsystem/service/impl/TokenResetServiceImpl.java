package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.PasswordResetToken;
import vn.com.carrentalsystem.repository.TokenRepository;
import vn.com.carrentalsystem.service.TokenResetService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TokenResetServiceImpl implements TokenResetService {

    private final TokenRepository tokenRepository;

    @Override
    public void deleteOldToken(PasswordResetToken passwordResetToken){
        tokenRepository.delete(passwordResetToken);
    }

    @Scheduled(fixedRate = 100*60*5) // Chạy mỗi 5 phút
    @Transactional
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteByExpiryDateTimeBefore(now);
    }
}
