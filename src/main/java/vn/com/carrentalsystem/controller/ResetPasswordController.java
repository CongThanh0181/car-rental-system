package vn.com.carrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.com.carrentalsystem.dto.UserDTO;
import vn.com.carrentalsystem.entities.PasswordResetToken;
import vn.com.carrentalsystem.entities.Userz;
import vn.com.carrentalsystem.repository.TokenRepository;
import vn.com.carrentalsystem.repository.UserzRepository;
import vn.com.carrentalsystem.service.TokenResetService;
import vn.com.carrentalsystem.service.UserzService;

@Controller
@RequiredArgsConstructor
public class ResetPasswordController {

	private final UserzService userDetailsService;

	private final UserzRepository userRepository;

	private final TokenRepository tokenRepository;

	private final TokenResetService tokenResetService;
	
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "forgot-password";
		//Todo
	}

	@PostMapping("/forgot-password")
	public String forgotPassordProcess(@ModelAttribute UserDTO userDTO) {
		String pathController = "redirect:/forgot-password";
		String param = "";

		// Tìm user xem có tồn tại không để reset, nếu không thì trả về notFound để báo lên UI
		Userz user = userRepository.findByEmail(userDTO.getEmail());
		if (user != null) {
			String output = userDetailsService.sendEmail(userDTO.getEmail());
			if ("success".equals(output)) {
				param = "?success";
			} else if ("mailHaveAldredysent".equals(output)) {
				param = "?mailHaveAldredysent";
			} else {
				param = "?error";
			}
		}
		return pathController + param;

	}

	@GetMapping("/reset-password/{token}")
	public String resetPasswordForm(@PathVariable String token, Model model) {
		String pathController = "redirect:/forgot-password?error";

		PasswordResetToken reset = tokenRepository.findByToken(token);
		if (reset != null && userDetailsService.hasExipred(reset.getExpiryDateTime())) {
			model.addAttribute("email", reset.getUser().getEmail());
			pathController = "reset-password";
		}
		return pathController;
	}
	
	@PostMapping("/reset-password")
	public String passwordResetProcess(@ModelAttribute UserDTO userDTO) {
		Userz user = userRepository.findByEmail(userDTO.getEmail());
		if(user != null) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userRepository.save(user);
			tokenResetService.deleteOldToken(user.getPasswordResetToken());
		}
		return "redirect:/login-signup";
	}

}
