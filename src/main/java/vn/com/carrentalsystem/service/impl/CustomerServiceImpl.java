package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.Car;
import vn.com.carrentalsystem.enums.Rate;
import vn.com.carrentalsystem.repository.CustomerRepository;
import vn.com.carrentalsystem.service.CustomerService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public Page<Car> getPagingSortSearching(String location,
                                            String pickUpDate,
                                            String pickUpTime,
                                            String dropOffDate,
                                            String dropOffTime,
                                            int page,
                                            int pageSize,
                                            String sort) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("car_id").ascending());

        if ("lastestToNewest".equals(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by("car_id").descending());
        }


        // Sử dụng numberOfDays để làm việc với truy vấn hoặc xử lý khác

        return this.customerRepository.searchingCars(location ,pageable);
    }


    @Override
    public Double AverageRate(Long carId) {
        List<Rate> rateList = customerRepository.RatingCar(carId);
        int total = 0;
        for (Rate rating : rateList) {
            total += rating.getStatus();
        }
        return (double) total / rateList.size();
    }

    @Override
    public Long NoOfRide(Long carId) {
        return customerRepository.NoOfRide(carId);
    }

}
