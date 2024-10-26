package vn.com.carrentalsystem.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.carrentalsystem.enums.CarStatus;
import vn.com.carrentalsystem.enums.FuelType;
import vn.com.carrentalsystem.enums.TransmissionType;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarDTO implements Serializable {

    private String licensePlate;

    private CarStatus carStatus;

    private Long brandName;

    private Long modelCar;

    private String color;

    private Integer productionYear;

    private Integer noOfSeats;

    private TransmissionType transmission;

    private FuelType fuel;

    private Integer mileage;

    private Double fuelConsumption;

    private Double basePrice;

    private Double deposit;

    private Long city;

    private Long district;

    private Long ward;

    private String streetHomeNumber;

    private String description;

    private String bluetooth;

    private String gps;

    private String camera;

    private String sunRoof;

    private String childLock;

    private String childSeat;

    private String dvd;

    private String usb;

    private String noSmoking;

    private String noPet;

    private String noFoodInCar;

    private String other;

    private String specifyDescription;

    private MultipartFile[] fileCarImage;

    private MultipartFile[] fileDocument;

}
