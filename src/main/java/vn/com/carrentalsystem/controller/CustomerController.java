package vn.com.carrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.carrentalsystem.dto.SeachCarDTO;
import vn.com.carrentalsystem.entities.Booking;
import vn.com.carrentalsystem.entities.Car;
import vn.com.carrentalsystem.entities.City;
import vn.com.carrentalsystem.entities.Userz;
import vn.com.carrentalsystem.enums.BookingStatus;

import vn.com.carrentalsystem.service.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final UserzService userzService;

    private final BookingService bookingService;

    private final CustomerService customerService;

    private final CarService carService;

    private final CityService cityService;



    @GetMapping("/home-customer/booking-list")
    public String showBookingList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "newestToLastest") String sort,
            Model model,
            Authentication authentication
    ) {

        // Xác định kích thước trang và trường sắp xếp
        int pageSize = 2;
        // Lấy danh sách xe phân trang
        Userz userz = userzService.findByEmail(authentication.getName());
        Page<Booking> bookingPage = bookingService.getBookingsByUserz(userz, page, pageSize, sort);
        model.addAttribute("totalItems", bookingPage.getTotalElements());
        for (Booking booking : bookingPage) {
            long differentInDays = Duration.between(booking.getStartDateTime(), booking.getEndDateTime()).toDays();
            model.addAttribute("numberOfDay", differentInDays);

        }
        // Đưa dữ liệu vào model để hiển thị trong view
        model.addAttribute("bookingList", bookingPage);
        return "booking-list";
    }

    @GetMapping("/home-customer/search-result/action")
    public String getPagingSortSearching(
            @ModelAttribute("seachCarDTO") SeachCarDTO seachCarDTO,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "newestToLastest") String sort,
            Model model) {
        int pageSize = 10;
        Page<Car> carPage = customerService.getPagingSortSearching(
                seachCarDTO.getLocation(), seachCarDTO.getPickUpDate(),
                seachCarDTO.getPickUpTime(), seachCarDTO.getDropOffDate(), seachCarDTO.getDropOffTime(), page, pageSize, sort);

        // Chuyển đổi pickUpDate, pickUpTime, dropOffDate và dropOffTime thành các đối tượng LocalDate và LocalTime
        LocalDate pickUpDateObj = LocalDate.parse(seachCarDTO.getPickUpDate());
        LocalDate dropOffDateObj = LocalDate.parse(seachCarDTO.getDropOffDate());

        // Tính số ngày từ pickUpDate đến DropOffDate
        long numberOfDays = ChronoUnit.DAYS.between(pickUpDateObj, dropOffDateObj);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", carPage.getTotalPages());
        model.addAttribute("totalItems", carPage.getTotalElements());
        model.addAttribute("listCars", carPage);
        model.addAttribute("numberOfDays", numberOfDays);
        model.addAttribute("searchCarDTO", seachCarDTO);

        return "search-result";
    }

    @GetMapping("/home-customer/search-result")
    public String showSearchResult(Model model) {
        List<Car> listCars = new ArrayList<>();

        model.addAttribute("totalItems", 0);
        model.addAttribute("listCars", listCars);
        model.addAttribute("searchCarDTO", new SeachCarDTO());
        return "search-result";
    }

    @GetMapping("/home-customer/cancel-booking")
    public String cancelBooking(@RequestParam("bookingId") Long bookingId) {
        Booking booking = bookingService.findById(bookingId);
        booking.setBookingStatus(BookingStatus.CANCEL);
        bookingService.save(booking);
        return "redirect:/home-customer/booking-list";
    }

    @GetMapping("/home-customer/book-car")
    public String showBookCar (Authentication authentication, Model model,
                              @RequestParam("carId") Long carId,
                              @RequestParam("location") String location,
                              @RequestParam("pickUpDate") String pickUpDate, @RequestParam("pickUpTime") String pickUpTime,
                              @RequestParam("dropOffDate") String dropOffDate, @RequestParam("dropOffTime") String dropOffTime) {

        Car car = carService.findById(carId);
        Userz userz = userzService.findByEmail(authentication.getName());

        List<City> cities = cityService.findAll();

        LocalDate localDatePickUpDate = LocalDate.parse(pickUpDate);
        LocalDate localDateDropOffDate = LocalDate.parse(dropOffDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Long numberOfDay = ChronoUnit.DAYS.between(localDatePickUpDate, localDateDropOffDate);
        numberOfDay = numberOfDay == 0 ? 1L : numberOfDay + 1L;


        model.addAttribute("location", location);
        model.addAttribute("pickUpDate", localDatePickUpDate.format(formatter));
        model.addAttribute("pickUpTime", pickUpTime);
        model.addAttribute("dropOffDate", localDateDropOffDate.format(formatter));
        model.addAttribute("dropOffTime", dropOffTime);
        model.addAttribute("numberOfDay", numberOfDay);
        model.addAttribute("car", car);
        model.addAttribute("userz", userz);
        model.addAttribute("cities", cities);
        return "book-car";
    }

    @GetMapping("/home-customer/car-detail")
    public String showCarDetail(@RequestParam("carId") Long carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "car-detail";
    }

    @GetMapping("/home-customer/confirm-pick-up-booking")
    public String confirmPickUpBooking(@RequestParam("bookingId") Long bookingId) {
        Booking booking = bookingService.findById(bookingId);
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        bookingService.save(booking);
        return "redirect:/home-customer/booking-list";
    }

    @GetMapping("/home-customer/return-car")
    public String returnCar(@RequestParam("bookingId") Long bookingId, Authentication authentication) {
        Userz userz = userzService.findByEmail(authentication.getName());
        Booking booking = bookingService.findById(bookingId);
        long differentInDays = Duration.between(booking.getStartDateTime(), booking.getEndDateTime()).toDays();
        Double amount = booking.getCar().getBasePrice() * differentInDays - booking.getCar().getDeposit();
        if(amount <= userz.getWallet().getBalance()){
            userz.getWallet().setBalance(userz.getWallet().getBalance() - amount);
            booking.setBookingStatus(BookingStatus.COMPLETED);
            bookingService.save(booking);
        }
        return "redirect:/home-customer/booking-list";
    }

}
