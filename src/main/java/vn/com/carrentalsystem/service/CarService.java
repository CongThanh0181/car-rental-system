package vn.com.carrentalsystem.service;

import org.springframework.data.domain.Page;
import vn.com.carrentalsystem.dto.CarDTO;
import vn.com.carrentalsystem.dto.CarFromCityDTO;
import vn.com.carrentalsystem.entities.Car;
import vn.com.carrentalsystem.entities.Userz;

import java.io.IOException;
import java.util.List;

public interface CarService {
    Car save(CarDTO carDTO, Userz userzCurrent) throws IOException;

    List<Car> findByLicensePlate(String licensePlate);

    List<CarFromCityDTO> countCarFromCity();

    Page<Car> getCarsByUserz(Userz userz, int page, int pageSize, String sort);

    Car findById(Long carId);

    void update(Long carId, CarDTO carDTO) throws IOException;
}
