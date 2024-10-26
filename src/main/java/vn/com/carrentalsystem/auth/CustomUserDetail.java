package vn.com.carrentalsystem.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.com.carrentalsystem.entities.Userz;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserDetail implements UserDetails {

    private Userz userz;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> result = new ArrayList<>();

        String roleUser = userz.getRole().name();

        result.add(new SimpleGrantedAuthority(roleUser));
        return result;
    }

    @Override
    public String getPassword() {
        return userz.getPassword();
    }

    @Override
    public String getUsername() {
        return userz.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullname() {
        return userz.getFullName();
    }

}
