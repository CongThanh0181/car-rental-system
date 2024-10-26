package vn.com.carrentalsystem.service;

import org.springframework.data.domain.Page;
import vn.com.carrentalsystem.entities.Car;

public interface CustomerService {
    Page<Car> getPagingSortSearching(String location,
                                     String pickUpDate,
                                     String pickUpTime,
                                     String dropOffDate,
                                     String dropOffTime,
                                     int page,
                                     int pageSize,
                                     String sort);

    Double AverageRate(Long carId);

    Long NoOfRide(Long carId);

}
