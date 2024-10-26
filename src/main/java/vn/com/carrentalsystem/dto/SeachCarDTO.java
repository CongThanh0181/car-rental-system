package vn.com.carrentalsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class SeachCarDTO {

    private String location;

    private String pickUpDate;

    private String pickUpTime;

    private String dropOffDate;

    private String dropOffTime;

}
