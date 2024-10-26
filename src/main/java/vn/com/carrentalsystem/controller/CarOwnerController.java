package vn.com.carrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.carrentalsystem.dto.CarDTO;
import vn.com.carrentalsystem.entities.*;
import vn.com.carrentalsystem.repository.*;
import vn.com.carrentalsystem.service.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarOwnerController {

    private final CityService cityService;

    private final BrandCarService brandCarService;

    private final CarService carService;

    private final UserzService userzService;

    private final ColorRepository colorRepository;
    private final NoOfSeatRepository noOfSeatRepository;
    private final ProductionYearRepository productionYearRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;

    @GetMapping("/home-carowner/add-car")
    public String showAddCarUI(Model model) {
        List<City> cities = cityService.findAll();
        List<BrandCar> brandCars = brandCarService.findAll();
        List<Color> colors = colorRepository.findAll();
        List<ProductionYear> productionYears = productionYearRepository.findAll();
        List<NoOfSeat> noOfSeats = noOfSeatRepository.findAll();

        model.addAttribute("cities", cities);
        model.addAttribute("brandCars", brandCars);
        model.addAttribute("colors", colors);
        model.addAttribute("productionYears", productionYears);
        model.addAttribute("noOfSeats", noOfSeats);

        return "add-car";
    }

    @PostMapping("/home-carowner/add-car/save")
    public String saveCar(@ModelAttribute CarDTO carDTO, Authentication authentication) throws IOException {
        Userz userzCurrent = userzService.findByEmail(authentication.getName());

        carService.save(carDTO, userzCurrent);

        return "redirect:/home-carowner/car-list";
    }

    @GetMapping("/home-carowner/car-list")
    public String showMyCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "newestToLastest") String sort,
            Model model,
            Authentication authentication
    ) {

        // Xác định kích thước trang và trường sắp xếp
        int pageSize = 1;

        // Lấy danh sách xe phân trang
        Userz userz = userzService.findByEmail(authentication.getName());
        Page<Car> carPage = carService.getCarsByUserz(userz, page, pageSize, sort);

        // Đưa dữ liệu vào model để hiển thị page trong view
        model.addAttribute("carList", carPage);
        return "car-list";
    }

    @GetMapping("/home-carowner/edit-car-detail")
    public String showCarDetail(@RequestParam("carId") Long carId, Model model) {
        Car car = carService.findById(carId);
        List<City> cities = cityService.findAll();
        District district =  districtRepository.findByDistrictName(car.getAddress().getDistrict());
        Ward ward = wardRepository.findByWardName(car.getAddress().getWard());

        model.addAttribute("car", car);
        model.addAttribute("cities", cities);
        model.addAttribute("district", district);
        model.addAttribute("ward", ward);
        return "edit-car-detail";
    }

    @PostMapping("/home-carowner/edit-car-detail/update")
    public String updateCar(@RequestParam("carId") Long carId, @ModelAttribute CarDTO carDTO) throws IOException {
        carService.update(carId, carDTO);
        return "redirect:/home-carowner/car-list";
    }

    @GetMapping("/home-carowner/my-report")
    public String showMyReport() {
        return "my-report";
    }


}
