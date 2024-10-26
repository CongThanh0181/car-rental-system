package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.dto.BookCarDTO;
import vn.com.carrentalsystem.entities.*;
import vn.com.carrentalsystem.enums.BookingStatus;
import vn.com.carrentalsystem.repository.*;
import vn.com.carrentalsystem.service.BookingService;
import vn.com.carrentalsystem.service.UserzService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final AddressRepository addressRepository;
    private final UserzDetailRepository userzDetailRepository;
    private final CarRepository carRepository;
    private final PaymentRepository paymentRepository;

    private final UserzService userzService;

    @Override
    public Page<Booking> getBookingsByUserz(Userz userz, int page, int pageSize, String sort) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("bookingId").ascending());

        if ("lastestToNewest".equals(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by("bookingId").descending());
        }

        return bookingRepository.findByUserz(userz, pageable);
    }

    @Override
    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    @Override
    public Booking save(BookCarDTO bookCarDTO, Userz userz) throws IOException {
        UserDetail userDetail = null;
        if (!bookCarDTO.getDriverName().isEmpty()) {
            City city = cityRepository.findById(bookCarDTO.getDriverCity()).orElse(null);
            District district = districtRepository.findById(bookCarDTO.getDriverDistrict()).orElse(null);
            Ward ward = wardRepository.findById(bookCarDTO.getDriverWard()).orElse(null);

            // handle address
            Address address = Address.builder()
                    .city(city.getCityName())
                    .district(district.getDistrictName())
                    .ward(ward.getWardName())
                    .streetHomeNumber(bookCarDTO.getDriverHouseNumberStreet())
                    .build();

            // handle user detail (Driver)
            userDetail = UserDetail.builder()
                    .nationalIdNo(bookCarDTO.getDriverNationalIdNo())
                    .dateOfBirth(bookCarDTO.getDriverDateOfBirth())
                    .phone(bookCarDTO.getDriverPhone())
                    .driverName(bookCarDTO.getDriverName())
                    .address(addressRepository.save(address))
                    .drivingLicense(userzService.saveImageLicense(bookCarDTO.getDriverDrivingLicense()))
                    .build();
        }

        Car carDB = carRepository.findById(bookCarDTO.getCarId()).orElse(null);

        // format date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(bookCarDTO.getPickUpDate() + " " + bookCarDTO.getPickUpTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(bookCarDTO.getDropOffDate() + " " + bookCarDTO.getDropOffTime(), formatter);

        // Handle booking
        Booking booking = Booking.builder()
                .bookingNo("B-"+UUID.randomUUID())
                .bookingStatus(BookingStatus.IN_PROGRESS)
                .car(carDB)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .userz(userz)
                .build();

        if (userDetail != null) {
            booking.setUserDetail(userzDetailRepository.save(userDetail));
        }

        // Handle payment
        Payment payment = Payment.builder()
                .totalAmount(bookCarDTO.getTotalAmount())
                .booking(bookingRepository.save(booking))
                .paymentMethod(bookCarDTO.getPaymentMethod())
                .build();
        paymentRepository.save(payment);


        return booking;
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
}
