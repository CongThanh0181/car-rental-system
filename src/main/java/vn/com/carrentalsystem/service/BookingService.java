package vn.com.carrentalsystem.service;

import org.springframework.data.domain.Page;
import vn.com.carrentalsystem.dto.BookCarDTO;
import vn.com.carrentalsystem.entities.Booking;
import vn.com.carrentalsystem.entities.Userz;

import java.io.IOException;

public interface BookingService {
    Page<Booking> getBookingsByUserz(Userz userz, int page, int pageSize, String sort);

    Booking findById(Long bookingId);

    Booking save(BookCarDTO bookCarDTO, Userz userz) throws IOException;

    Booking save(Booking booking);
}
