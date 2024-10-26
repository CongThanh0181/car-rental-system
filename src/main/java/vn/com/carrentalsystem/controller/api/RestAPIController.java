package vn.com.carrentalsystem.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.carrentalsystem.dto.BookCarDTO;
import vn.com.carrentalsystem.dto.BookingResponseDTO;
import vn.com.carrentalsystem.dto.CarResponseDTO;
import vn.com.carrentalsystem.dto.UserDetailDTO;

import vn.com.carrentalsystem.entities.*;
import vn.com.carrentalsystem.repository.*;
import vn.com.carrentalsystem.service.BookingService;
import vn.com.carrentalsystem.service.CarService;
import vn.com.carrentalsystem.service.CustomerService;
import vn.com.carrentalsystem.service.UserzService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequiredArgsConstructor
public class RestAPIController {

    private final ModelCarRepository modelCarRepository;

    private final DistrictRepository districtRepository;

    private final WardRepository wardRepository;

    private final CarService carService;

    private final BookingService bookingService;

    private final UserzService userzService;

    private final CustomerService customerService;

    private final WalletRepository walletRepository;

    private final PaymentTransactionRepository paymentTransactionRepository;


    @GetMapping("/api/model-cars")
    public ResponseEntity<List<ModelCar>> getModelCarByBrandId(@RequestParam("brandId") Long brandId) {
        // Lấy danh sách cars có brandId tương ứng từ repository
        List<ModelCar> modelCars = modelCarRepository.findAllByBrandCarId(brandId);

        return ResponseEntity.ok(modelCars);
    }

    @GetMapping("/api/districts")
    public ResponseEntity<List<District>> getDistrictByCityId(@RequestParam("cityId") Long cityId) {
        // Lấy danh sách District có CityId tương ứng từ repository
        List<District> districtList = districtRepository.findAllByCityId(cityId);

        return ResponseEntity.ok(districtList);
    }

    @GetMapping("/api/wards")
    public ResponseEntity<List<Ward>> getWardByDistrictId(@RequestParam("districtId") Long districtId) {
        // Lấy danh sách Ward có districtId tương ứng từ repository
        List<Ward> wardList = wardRepository.findAllByDistrictId(districtId);

        return ResponseEntity.ok(wardList);
    }

    @GetMapping("/api/home-carowner/license-plate")
    public ResponseEntity getCarByLicensePlate(@RequestParam("licensePlate") String licensePlate) {
        // Kiểm tra xem có trùng licensePlate
        List<Car> carList = carService.findByLicensePlate(licensePlate);
        String result = "fail";
        if (carList.isEmpty()) {
            result = "ok";
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/home-carowner/car-list")
    public ResponseEntity<List<CarResponseDTO>> getCarList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "newestToLastest") String sort,
            Authentication authentication
    ) {

        // Xác định kích thước trang và trường sắp xếp
        int pageSize = 1;

        // Lấy danh sách xe phân trang
        Userz userz = userzService.findByEmail(authentication.getName());
        Page<Car> carPage = carService.getCarsByUserz(userz, page, pageSize, sort);

        // Convert sang CarResponseDTO để tránh stackoverflow do chuyển đổi Json
        List<CarResponseDTO> carResponseDTOList = new ArrayList<>();

        for (Car car : carPage) {
            CarResponseDTO carResponseDTO = CarResponseDTO.builder()
                    .carId(car.getCarId())
                    .brand(car.getBrand())
                    .model(car.getModel())
                    .productionYear(car.getProductionYear())
                    .city(car.getAddress().getCity())
                    .district(car.getAddress().getDistrict())
                    .mileage(car.getMileage())
                    .basePrice(car.getBasePrice())
                    .carStatus(car.getCarStatus().getStatus())
                    .urlImageFront(car.getImageCar().getUrlImageFront())
                    .urlImageBack(car.getImageCar().getUrlImageBack())
                    .urlImageLeft(car.getImageCar().getUrlImageLeft())
                    .urlImageRight(car.getImageCar().getUrlImageRight())
                    .build();
            carResponseDTOList.add(carResponseDTO);
        }

        return ResponseEntity.ok(carResponseDTOList);
    }


    @GetMapping("/api/home-customer/booking-list")
    public ResponseEntity<List<BookingResponseDTO>> getBookingList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "newestToLastest") String sort,
            Authentication authentication
    ) {
        // Xác định kích thước trang và trường sắp xếp
        int pageSize = 2;
        // Lấy danh sách xe phân trang
        Userz userz = userzService.findByEmail(authentication.getName());
        Page<Booking> bookingPage = bookingService.getBookingsByUserz(userz, page, pageSize, sort);

        List<BookingResponseDTO> bookingResponseDTOList = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm a");
        for (Booking booking : bookingPage) {
            long differentInDays = Duration.between(booking.getStartDateTime(), booking.getEndDateTime()).toDays();
            String formattedStartDateTime = booking.getStartDateTime().format(dateTimeFormatter);
            String formattedEndDateTime = booking.getEndDateTime().format(dateTimeFormatter);
            String notification = getNotification(booking, differentInDays, userz);

            BookingResponseDTO bookingResponseDTO = BookingResponseDTO.builder()
                    .bookingId(booking.getBookingId())
                    .carId(booking.getCar().getCarId())
                    .bookingStatus(booking.getBookingStatus())
                    .bookingNo(booking.getBookingNo())
                    .deposit(booking.getCar().getDeposit())
                    .numberOfDay(differentInDays)
                    .endDateTime(formattedEndDateTime)
                    .startDateTime(formattedStartDateTime)
                    .basePrice(booking.getCar().getBasePrice())
                    .brand(booking.getCar().getBrand())
                    .model(booking.getCar().getModel())
                    .productionYear(booking.getCar().getProductionYear())
                    .urlImageFront(booking.getCar().getImageCar().getUrlImageFront())
                    .urlImageRight(booking.getCar().getImageCar().getUrlImageRight())
                    .urlImageBack(booking.getCar().getImageCar().getUrlImageBack())
                    .urlImageLeft(booking.getCar().getImageCar().getUrlImageLeft())
                    .notification(notification)
                    .build();
            bookingResponseDTOList.add(bookingResponseDTO);
        }
        return ResponseEntity.ok(bookingResponseDTOList);
    }

    private static String getNotification(Booking booking, long differentInDays, Userz userz) {
        double amount = booking.getCar().getBasePrice() * differentInDays - booking.getCar().getDeposit();
        String notification;
        if (amount > userz.getWallet().getBalance()) {
            notification = "Please confirm to return the car. The exceeding amount of " + amount + " VND will be deducted from your wallet.";
        } else {
            notification = "Please confirm to return the car. The remaining amount of " + amount + " VND will be deducted from your wallet.";
        }
        return notification;
    }


//    @GetMapping("/api/home-customer/search-result")
//    public ResponseEntity<List<SearchCarResponseDTO>> getSearchResultList(
//            @ModelAttribute("seachCarDTO") SeachCarDTO seachCarDTO,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(value = "sort", required = false, defaultValue = "newestToLastest") String sort
//
//    ) {
//        // Xác định kích thước trang và trường sắp xếp
//        int pageSize = 2;
//        // Lấy danh sách xe phân trang
//        Page<Car> carPage = customerService.getPagingSortSearching(
//                seachCarDTO.getLocation(), seachCarDTO.getPickUpDate(),
//                seachCarDTO.getPickUpTime(), seachCarDTO.getDropOffDate(), seachCarDTO.getDropOffTime(), page, pageSize, sort);
//        // Convert sang CarResponseDTO để tránh stackoverflow do chuyển đổi Json
//        List<SearchCarResponseDTO> searchCarResponseDTOList = new ArrayList<>();
//
//
//        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
//
//        for (Car car : carPage) {
//
//
//            String formattedValue = decimalFormat.format(car.getBasePrice());
//            SearchCarResponseDTO searchCarResponseDTO = SearchCarResponseDTO.builder()
//                    .carId(car.getCarId())
//                    .modelCar(car.getBrand() + " " + car.getModel() + " " + car.getProductionYear())
//                    .location(car.getAddress().getStreetHomeNumber() + " " + car.getAddress().getDistrict() + " " + car.getAddress().getCity())
//                    .basePrice(formattedValue)
//                    .urlImageFront(car.getImageCar().getUrlImageFront())
//                    .urlImageBack(car.getImageCar().getUrlImageBack())
//                    .urlImageLeft(car.getImageCar().getUrlImageLeft())
//                    .urlImageRight(car.getImageCar().getUrlImageRight())
//                    .noOfRides(customerService.NoOfRide(car.getCarId()))
//                    .rate(customerService.AverageRate(car.getCarId()))
//                    .carStatus(car.getCarStatus())
//                    .build();
//            searchCarResponseDTOList.add(searchCarResponseDTO);
//        }
//
//        return ResponseEntity.ok(searchCarResponseDTOList);
//    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody UserDetailDTO userDetailDTO, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        // Xử lý yêu cầu thay đổi mật khẩu ở đây
        String output = userzService.updateUserPassword(userDetailDTO, authentication);
        String message = "Please input correct password as validation";
        if ("success".equals(output)) {
            message = "Password change success";
        }
        if ("invalidPasswordConfirm".equals(output)) {
            message = "Password and confirm password is not same";
        }
        if ("wrongPassword".equals(output)) {
            message = "Old password isn't correct";
        }
        // Trả về phản hồi JSON
        response.put("message", message);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/api/book-car/save")
    public ResponseEntity<Booking> saveBookCar(BookCarDTO bookCarDTO, Authentication authentication) throws IOException {
        Userz userz = userzService.findByEmail(authentication.getName());
        Booking booking = bookingService.save(bookCarDTO, userz);
        return ResponseEntity.ok(booking);
    }


    @PostMapping("/withdrawForm")
    public String submitForm(String withdrawNumber, Authentication authentication) {
        Userz userzDB = userzService.findByEmail(authentication.getName());
        Wallet wallet = userzDB.getWallet();


        // Khởi tạo một StringBuilder để lưu trữ các số
        StringBuilder number = new StringBuilder();

        // Lặp qua từng ký tự trong chuỗi
        for (char c : withdrawNumber.toCharArray()) {
            // Kiểm tra xem ký tự hiện tại có phải là số không
            if (Character.isDigit(c)) {
                // Nếu là số, thêm vào StringBuilder
                number.append(c);
            }
        }
        double withdraw = Double.parseDouble(String.valueOf(number));
        if (wallet.getBalance() >= withdraw) {
            wallet.setBalance(wallet.getBalance() - withdraw);
            walletRepository.save(wallet);
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setAmount(withdraw);
            paymentTransaction.setType("withdraw");
            paymentTransaction.setWallet(wallet);
            paymentTransaction.setDateTime(LocalDateTime.now());
            paymentTransactionRepository.save(paymentTransaction);
        }
        return "redirect:/wallet";
    }

    @PostMapping("/topUpForm")
    public String submitFormTopUp(String topUpNumber, Authentication authentication) {
        Userz userzDB = userzService.findByEmail(authentication.getName());
        Wallet wallet = userzDB.getWallet();
        // Khởi tạo một StringBuilder để lưu trữ các số
        StringBuilder number = new StringBuilder();
        // Lặp qua từng ký tự trong chuỗi
        for (char c : topUpNumber.toCharArray()) {
            // Kiểm tra xem ký tự hiện tại có phải là số không
            if (Character.isDigit(c)) {
                // Nếu là số, thêm vào StringBuilder
                number.append(c);
            }
        }
        double topUp = Double.parseDouble(String.valueOf(number));
        wallet.setBalance(wallet.getBalance() + topUp);
        walletRepository.save(wallet);
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setAmount(topUp);
        paymentTransaction.setType("top-up");
        paymentTransaction.setWallet(wallet);
        paymentTransaction.setDateTime(LocalDateTime.now());
        paymentTransactionRepository.save(paymentTransaction);
        return "redirect:/wallet";
    }
}

