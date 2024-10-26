package vn.com.carrentalsystem.service;

import vn.com.carrentalsystem.entities.PasswordResetToken;

public interface TokenResetService {
    void deleteOldToken(PasswordResetToken passwordResetToken);
}
