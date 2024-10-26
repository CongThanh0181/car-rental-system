package vn.com.carrentalsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.carrentalsystem.entities.Car;
import vn.com.carrentalsystem.enums.Rate;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Car, Long> {

    @Override
    Page<Car> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM car c " +
            "LEFT JOIN address a ON c.address_id = a.address_id " +
            "WHERE (a.city LIKE CONCAT('%', :search, '%') " +
            "   OR a.streest_home_number LIKE CONCAT('%', :search, '%') " +
            "   OR a.district LIKE CONCAT('%', :search, '%')) " +
            "   AND c.car_status = 'AVAILABLE'", nativeQuery = true)
    Page<Car> searchingCars(@Param("search") String search, Pageable pageable);

    @Query(value = "\n" +
            "SELECT f.rate" +
            "  FROM car c\n" +
            "  JOIN booking b\n" +
            "  ON c.car_id = b.car_id\n" +
            "  JOIN feed_back f\n" +
            "  ON b.booking_id = f.booking_id" +
            "  WHERE c.car_id = :carId", nativeQuery = true)
    List<Rate> RatingCar(@Param("carId") Long carId);

    @Query(value = "\n" +
            "SELECT COUNT(b.booking_status)\n" +
            "  FROM car c\n" +
            "  JOIN booking b\n" +
            "  ON c.car_id = b.car_id\n" +
            "WHERE b.booking_status = 'COMPLETE'" +
            "     AND c.car_id = :carId", nativeQuery = true)
    Long NoOfRide(@Param("carId") Long carId);
}

