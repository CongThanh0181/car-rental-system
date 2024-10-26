package vn.com.carrentalsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.carrentalsystem.entities.Booking;
import vn.com.carrentalsystem.entities.Userz;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByUserz(Userz userz, Pageable pageable);
}
