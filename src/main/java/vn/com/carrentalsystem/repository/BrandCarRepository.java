package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.BrandCar;

@Repository
public interface BrandCarRepository extends JpaRepository<BrandCar, Long> {
}
