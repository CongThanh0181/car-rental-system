package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.ModelCar;

import java.util.List;

@Repository
public interface ModelCarRepository extends JpaRepository<ModelCar, Long> {

    @Query(nativeQuery = true, value = "SELECT m.* FROM model_car AS m WHERE m.brand_car_id = :brandId")
    List<ModelCar> findAllByBrandCarId(Long brandId);
}
