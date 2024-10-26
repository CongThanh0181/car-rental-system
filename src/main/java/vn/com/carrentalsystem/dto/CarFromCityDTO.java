package vn.com.carrentalsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarFromCityDTO {
    private String city;

    private Long carCount;
}
