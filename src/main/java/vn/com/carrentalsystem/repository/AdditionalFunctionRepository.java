package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.AdditionalFunction;

@Repository
public interface AdditionalFunctionRepository extends JpaRepository<AdditionalFunction, Long> {
}
