package vn.com.carrentalsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.dto.CarFromCityDTO;
import vn.com.carrentalsystem.entities.Car;
import vn.com.carrentalsystem.entities.Userz;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByLicensePlate(String licensePlate);

    @Query(value = "SELECT new vn.com.carrentalsystem.dto.CarFromCityDTO(a.city, COUNT(c.carId)) " +
                    "FROM Address AS a " +
                    "JOIN Car AS c " +
                    "ON a.addressId = c.address.addressId " +
                    "GROUP BY a.city")
    List<CarFromCityDTO> countCarFromCity();

    Page<Car> findByUserz(Userz userz, Pageable pageable);
}
