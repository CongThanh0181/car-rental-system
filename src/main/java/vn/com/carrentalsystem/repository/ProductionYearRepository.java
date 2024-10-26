package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.ProductionYear;

@Repository
public interface ProductionYearRepository extends JpaRepository<ProductionYear, Long> {

}
