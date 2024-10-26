package vn.com.carrentalsystem.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import vn.com.carrentalsystem.dto.RegisterDTO;
import vn.com.carrentalsystem.dto.UserDetailDTO;
import vn.com.carrentalsystem.entities.DrivingLicense;
import vn.com.carrentalsystem.entities.Userz;

import java.io.IOException;
import java.time.LocalDateTime;

public interface UserzService {

    String registerUser (RegisterDTO registerDTO);

    Userz findByEmail(String email);

    String sendEmail(String email);

    String generateResetToken(Userz user);

    boolean hasExipred(LocalDateTime expiryDateTime);

    Userz updateUserz(Userz userz);

    String updateUserPassword(UserDetailDTO userDetailDto, Authentication authentication);

    void updateUserInformation(UserDetailDTO userDetailDto, Authentication authentication) throws IOException;

    DrivingLicense saveImageLicense(MultipartFile multipartLicenseFiles) throws IOException;

}
