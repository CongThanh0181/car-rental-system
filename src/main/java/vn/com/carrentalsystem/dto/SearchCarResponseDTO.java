package vn.com.carrentalsystem.dto;

import lombok.*;
import vn.com.carrentalsystem.enums.CarStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchCarResponseDTO {

    private Long carId;

    private String modelCar;

    private String basePrice;

    private CarStatus carStatus;

    private String urlImageFront;

    private String urlImageBack;

    private String urlImageRight;

    private String urlImageLeft;

    private String location;

    private Double rate;

    private Long noOfRides;
}
