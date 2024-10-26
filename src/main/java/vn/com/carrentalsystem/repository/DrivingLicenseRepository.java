package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.DrivingLicense;

@Repository
public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, Long> {
}
