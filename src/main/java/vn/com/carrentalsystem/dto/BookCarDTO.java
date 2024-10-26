package vn.com.carrentalsystem.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import vn.com.carrentalsystem.enums.PaymentMethod;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookCarDTO {

    private String driverName;
    private String driverPhone;
    private String driverNationalIdNo;
    private String driverEmail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date driverDateOfBirth;

    private MultipartFile driverDrivingLicense;
    private Long driverCity;
    private Long driverDistrict;
    private Long driverWard;
    private String driverHouseNumberStreet;
    private PaymentMethod paymentMethod;
    private Long carId;
    private String pickUpDate;
    private String pickUpTime;
    private String dropOffDate;
    private String dropOffTime;
    private Double totalAmount;

}
