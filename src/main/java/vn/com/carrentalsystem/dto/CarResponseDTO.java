package vn.com.carrentalsystem.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CarResponseDTO {

    private Long carId;

    private String brand;

    private String model;

    private Integer productionYear;

    private Integer mileage;

    private Double basePrice;

    private String city;

    private String district;

    private String carStatus;

    private String urlImageFront;

    private String urlImageBack;

    private String urlImageRight;

    private String urlImageLeft;
}
