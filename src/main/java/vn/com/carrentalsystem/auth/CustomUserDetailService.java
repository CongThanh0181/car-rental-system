package vn.com.carrentalsystem.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.Userz;
import vn.com.carrentalsystem.service.UserzService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserzService userzService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Userz userzDB = userzService.findByEmail(email);

        if (userzDB == null) {
            throw new UsernameNotFoundException("Username is not found");
        }

        CustomUserDetail customUserDetail = new CustomUserDetail();
        customUserDetail.setUserz(userzDB);
        return customUserDetail;
    }
}
