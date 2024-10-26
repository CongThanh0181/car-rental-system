package vn.com.carrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.carrentalsystem.dto.CarFromCityDTO;
import vn.com.carrentalsystem.dto.UserDetailDTO;
import vn.com.carrentalsystem.entities.*;
import vn.com.carrentalsystem.repository.DistrictRepository;
import vn.com.carrentalsystem.repository.WalletRepository;
import vn.com.carrentalsystem.repository.WardRepository;
import vn.com.carrentalsystem.service.CarService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.com.carrentalsystem.dto.RegisterDTO;

import vn.com.carrentalsystem.service.CityService;
import vn.com.carrentalsystem.service.UserzService;
import vn.com.carrentalsystem.service.impl.UserzServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserzService userzService;

    private final CarService carService;

    private final CityService cityService;

    private final DistrictRepository districtRepository;

    private final WardRepository wardRepository;

    private final UserzServiceImpl userzServiceI;

    private final WalletRepository walletRepository;

    @GetMapping({"/", "", "/home"})
    public String showHomePage(Model model) {
        List<CarFromCityDTO> carFromCityDTOList = carService.countCarFromCity();
        model.addAttribute("carFromCityDTOList", carFromCityDTOList);
        //TODO where to find us?

        return "home";
    }

    @GetMapping("/login-signup")
    public String showLoginSignup() {
        return "login-signup";
    }

    @GetMapping("/home-customer")
    public String showHomePageCustomer() {
        return "home-customer";
    }

    @GetMapping("/home-carowner")
    public String showHomePageCarOwner() {
        return "home-carowner";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/home";
    }

    // Register
    @PostMapping("/login-signup")
    public String register(@ModelAttribute RegisterDTO registerDTO ){
        Userz userz = userzService.findByEmail(registerDTO.getEmail());
        String pathController = "redirect:/login-signup";
        String param;

        if(userz != null) {
            param = "?exsitEmail";
        } else {
            String output = userzService.registerUser(registerDTO);
            if ("success".equals(output)) {
                param = "?success";
            } else {
                param = "?fail";
            }
        }

        return pathController + param;
    }

    @GetMapping("/edit-profile")
    public String showEditProfile(Model model,Authentication authentication) {
        Userz userzDB = userzService.findByEmail(authentication.getName());
        List<City> cities = cityService.findAll();
        model.addAttribute("cities", cities);
        model.addAttribute("userz", userzDB);

        if(userzDB.getUserDetail().getAddress() == null){
            model.addAttribute("district",null);
            model.addAttribute("ward",null);
        } else {
            District district =  districtRepository.findByDistrictName(userzDB.getUserDetail().getAddress().getDistrict());
            Ward ward = wardRepository.findByWardName(userzDB.getUserDetail().getAddress().getWard());
            model.addAttribute("district",district);
            model.addAttribute("ward",ward);
        }

        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String postEditProfile(@ModelAttribute UserDetailDTO userDetailDto, Authentication authentication) throws IOException {
        String pathController = "redirect:/edit-profile";
        String param = "";

        if(userDetailDto.getOldPassword() != null) {
            String output = userzServiceI.updateUserPassword(userDetailDto, authentication);
            if ("success".equals(output)) {
                param = "?success";
            }
            if ("invalidPasswordConfirm".equals(output)) {
                param = "?invalidPasswordConfirm";
            }
            if ("wrongPassword".equals(output)) {
                param = "?wrongPassword";
            }
        }else {
            userzServiceI.updateUserInformation(userDetailDto,authentication);
        }
        return pathController + param;
    }

    @GetMapping("/wallet")
    public String showWallet(Authentication authentication, Model model) {
        Userz userzDB = userzService.findByEmail(authentication.getName());
        Wallet wallet = userzDB.getWallet();

        if(wallet.getBalance() == null)
        {
            wallet.setBalance(100000D);
            walletRepository.save(wallet);
        }
        model.addAttribute("amountWallet",wallet.getBalance());
        return "wallet";
    }

}
