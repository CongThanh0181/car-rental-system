package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.ImageCar;

@Repository
public interface ImageCarRepository extends JpaRepository<ImageCar, Long> {
}
