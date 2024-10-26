package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.carrentalsystem.dto.RegisterDTO;
import vn.com.carrentalsystem.dto.UserDetailDTO;
import vn.com.carrentalsystem.entities.*;
import vn.com.carrentalsystem.repository.*;
import vn.com.carrentalsystem.service.CityService;
import vn.com.carrentalsystem.service.UserzService;
import vn.com.carrentalsystem.utils.StringProcessorUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import static vn.com.carrentalsystem.enums.Role.ROLE_CAROWNER;
import static vn.com.carrentalsystem.enums.Role.ROLE_CUSTOMER;

@Service
@Transactional
@RequiredArgsConstructor
public class UserzServiceImpl implements UserzService {

    private final UserzRepository userzRepository;

    private final UserzDetailRepository userzDetailRepository;

    private final JavaMailSender javaMailSender;

    private final TokenRepository tokenRepository;

    private final WalletRepository walletRepository;

    private final DrivingLicenseRepository drivingLicenseRepository;

    public final PasswordEncoder passwordEncoder;

    private final CityService cityService;

    private final DistrictRepository districtRepository;

    private final WardRepository wardRepository;

    private  final AddressRepository addressRepository;


    @Override
    public String registerUser(RegisterDTO registerDTO) {
        String result = "fail";

        if (registerDTO.getPassword().equals(registerDTO.getConfirmPassword())
                && ("agree".equals(registerDTO.getAgree()))
                && (ROLE_CUSTOMER.equals(registerDTO.getRole()) || ROLE_CAROWNER.equals(registerDTO.getRole()))
        ) {
            Userz userz = new Userz();
            userz.setFullName(StringProcessorUtil.processFullName(registerDTO.getName()));
            userz.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            userz.setRole(registerDTO.getRole());
            userz.setEmail(registerDTO.getEmail());

            DrivingLicense drivingLicense = new DrivingLicense();
            drivingLicenseRepository.save(drivingLicense);

            UserDetail userDetail = new UserDetail();
            userDetail.setPhone(registerDTO.getPhone());
            userDetail.setDrivingLicense(drivingLicense);
            userDetail.setNationalIdNo(drivingLicense.getDrivingLicenseId().toString());
            userzDetailRepository.save(userDetail);

            Wallet wallet = new Wallet();
            walletRepository.save(wallet);

            userz.setUserDetail(userDetail);
            userz.setWallet(wallet);
            userzRepository.save(userz);
            result = "success";
        }
        return result;
    }

    @Override
    public Userz findByEmail(String email) {
        return userzRepository.findByEmail(email);
    }

    @Override
    public String sendEmail(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Userz userzDB = userzRepository.findByEmail(email);

        if (tokenRepository.findByUser(userzDB.getUserId()) != null) {
            return "mailHaveAldredysent";
        }
        String resetPasswordUrl = generateResetToken(userzDB);
        String content = "Click on this link to reset password: " + resetPasswordUrl;
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo(userzDB.getEmail());
            message.setSubject("Reset Password For: " + userzDB.getFullName());
            message.setText(content);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            return "error";
        }
        return "success";
    }

    @Override
    public String generateResetToken(Userz user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(5);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setUser(user);
        PasswordResetToken token = tokenRepository.save(resetToken);
        if (token != null) {
            String endpointUrl = "http://localhost:8080/reset-password";
            //TODO: SUA LAI DUONG DAN
            return endpointUrl + "/" + resetToken.getToken();
        }
        return "";
    }

    @Override
    public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }

    @Override
    public Userz updateUserz(Userz userz) {
        return userzRepository.save(userz);
    }


    @Override
    public DrivingLicense saveImageLicense(MultipartFile multipartLicenseFiles) throws IOException {
        DrivingLicense drivingLicense = new DrivingLicense();
        String imageName = UUID.randomUUID() + "-" + StringUtils.cleanPath(multipartLicenseFiles.getOriginalFilename());

        // Lưu đường dẫn vào Cơ sở dữ liệu
        drivingLicense.setUrlImage(imageName);

        // Lưu ảnh giấy phép lái xe vào Cơ sở dữ liệu
        DrivingLicense imageLicenseDb = drivingLicenseRepository.save(drivingLicense);

        // Đường dẫn lưu file tính từ thư mục gốc project
        String uploadImageCarDir = "./src/main/resources/static/image/driving-license/" + imageLicenseDb.getDrivingLicenseId();

        // Lưu file vào local
        FileServiceImpl.saveFile(uploadImageCarDir, multipartLicenseFiles, imageLicenseDb.getUrlImage());
        return imageLicenseDb;
    }


    public DrivingLicense updateImageLicense(MultipartFile multipartFiles, DrivingLicense drivingLicense) throws IOException {
        // Đường dẫn lưu file tính từ thư mục gốc project
        String uploadImageCarDir = "./src/main/resources/static/image/driving-license/" + drivingLicense.getDrivingLicenseId();
        if (!multipartFiles.isEmpty()) {
            String imageName = UUID.randomUUID() + "-" + StringUtils.cleanPath(multipartFiles.getOriginalFilename());
            // Lưu đường dẫn vào Database và cập nhật hình ảnh mới
            // Xóa hình ảnh cũ
            File imageFile = new File(uploadImageCarDir + "/" + drivingLicense.getUrlImage());
            FileSystemUtils.deleteRecursively(imageFile);

            // Cập nhật tên hình ảnh mới
            drivingLicense.setUrlImage(imageName);
        }
        // Cập nhật hình ảnh giấy phép lái xe vào Database
        drivingLicenseRepository.save(drivingLicense);
        // Lưu file mới vào local
        if (!multipartFiles.isEmpty()) {
            FileServiceImpl.saveFile(uploadImageCarDir, multipartFiles, drivingLicense.getUrlImage());
        }
        return drivingLicense;
    }

    @Override
    public void updateUserInformation(UserDetailDTO userDetailDto, Authentication authentication) throws IOException {
        Userz userzDB = findByEmail(authentication.getName());
        userzDB.setFullName(StringProcessorUtil.processFullName(userDetailDto.getFullName()));
        userzDB.getUserDetail().setPhone(userDetailDto.getPhone());
        userzDB.getUserDetail().setNationalIdNo(userDetailDto.getNationalIdNo());
        userzDB.getUserDetail().setDateOfBirth(userDetailDto.getDateOfBirth());

        Address address = new Address();
        addressRepository.save(address);
        userzDB.getUserDetail().setAddress(address);

        String city = cityService.findByCityId(Long.parseLong(userDetailDto.getCity())).getCityName();
        userzDB.getUserDetail().getAddress().setCity(city);

        String district = districtRepository.findByDistrictId(Long.parseLong(userDetailDto.getDistrict())).getDistrictName();
        userzDB.getUserDetail().getAddress().setDistrict(district);

        String ward = wardRepository.findByWardId(Long.parseLong(userDetailDto.getWard())).getWardName();
        userzDB.getUserDetail().getAddress().setWard(ward);

        userzDB.getUserDetail().getAddress().setStreetHomeNumber(userDetailDto.getStreetHomeNumber());
        if(userzDB.getUserDetail().getDrivingLicense().getDrivingLicenseId() == null)
        {
            saveImageLicense(userDetailDto.getFileLicenseImg());
        }
        {
            updateImageLicense(userDetailDto.getFileLicenseImg(),userzDB.getUserDetail().getDrivingLicense());
        }
        updateUserz(userzDB);
    }

    @Override
    public String updateUserPassword(UserDetailDTO userDetailDto, Authentication authentication) {
        Userz userzDB = findByEmail(authentication.getName());
        String result = "notValidPassword";
        if(userDetailDto.getOldPassword() != null && userDetailDto.getNewPassword() != null && userDetailDto.getConfirmPassword() != null) {

            if (passwordEncoder.matches(userDetailDto.getOldPassword(), userzDB.getPassword())) {

                if (userDetailDto.getNewPassword().equals(userDetailDto.getConfirmPassword())) {
                    userzDB.setPassword(passwordEncoder.encode(userDetailDto.getNewPassword()));
                    userzRepository.save(userzDB);
                    result = "success";
                } else {
                    result = "invalidPasswordConfirm";
                }

            } else {
                result = "wrongPassword";
            }
        }

        return result;
    }
}
