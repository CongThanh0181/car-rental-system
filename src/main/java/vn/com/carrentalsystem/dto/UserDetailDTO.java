package vn.com.carrentalsystem.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserDetailDTO {
    private String fullName;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String phone;

    private String nationalIdNo;

    private String district;

    private String city;

    private String ward;

    private String streetHomeNumber;

    private MultipartFile fileLicenseImg;

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
