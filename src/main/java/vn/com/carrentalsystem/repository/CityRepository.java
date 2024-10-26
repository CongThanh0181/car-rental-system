package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
