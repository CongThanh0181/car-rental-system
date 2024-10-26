package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.District;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query(nativeQuery = true, value = "SELECT d.* FROM district AS d WHERE d.city_id = :cityId")
    List<District> findAllByCityId(Long cityId);

    District findByDistrictName(String district);

    District findByDistrictId(Long id);
}
