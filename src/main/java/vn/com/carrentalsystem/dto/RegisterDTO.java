package vn.com.carrentalsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.carrentalsystem.enums.Role;
@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {
    private String name;

    private String email;

    private String phone;

    private String password;

    private String confirmPassword;

    private Role role;

    private String agree;

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", role=" + role +
                ", agree='" + agree + '\'' +
                '}';
    }
}
