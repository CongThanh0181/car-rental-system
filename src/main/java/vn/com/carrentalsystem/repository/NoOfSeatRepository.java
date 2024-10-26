package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.NoOfSeat;

@Repository
public interface NoOfSeatRepository extends JpaRepository<NoOfSeat, Long> {

}
