package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.Ward;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {

    @Query(nativeQuery = true, value = "SELECT w.* FROM ward AS w WHERE w.district_id = :districtId")
    List<Ward> findAllByDistrictId(Long districtId);

    Ward findByWardName(String ward);

    Ward findByWardId(Long id);
}
