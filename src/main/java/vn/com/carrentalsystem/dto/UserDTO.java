package vn.com.carrentalsystem.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO implements Serializable {

	private String name;

	private String email;

	private String password;

	private String phone;

	private String nationalIdNo;

	private Date dateOfBirth;

	private MultipartFile multipartFileLicense;

	private String city;

	private String district;

	private String ward;

	private String streetHomeNumber;


}
