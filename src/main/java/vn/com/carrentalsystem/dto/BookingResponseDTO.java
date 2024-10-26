package vn.com.carrentalsystem.dto;

import lombok.*;
import vn.com.carrentalsystem.enums.BookingStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookingResponseDTO {

    private Long bookingId;

    private Long carId;

    private String bookingNo;

    private String startDateTime;

    private String endDateTime;

    private BookingStatus bookingStatus;

    private Double basePrice;

    private Long numberOfDay;

    private Double deposit;

    private String brand;

    private String model;

    private Integer productionYear;

    private String urlImageFront;

    private String urlImageBack;

    private String urlImageRight;

    private String urlImageLeft;

    private String notification;
}
