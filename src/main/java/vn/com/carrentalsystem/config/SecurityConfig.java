package vn.com.carrentalsystem.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import vn.com.carrentalsystem.enums.Role;

import javax.servlet.http.HttpSession;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    public final UserDetailsService userDetailsService;

    public final PasswordEncoder passwordEncoder;

    @Autowired
    @SneakyThrows
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/", "/home", "/login-signup", "/image/**","/css/**" ,"/js/**",
                                "/bootstrap-4.6.2-dist/**","/forgot-password/**","/reset-password/**").permitAll()
                        .antMatchers("/home-customer/**").hasAnyAuthority(Role.ROLE_CUSTOMER.name(), Role.ROLE_ADMIN.name())
                        .antMatchers("/home-carowner/**").hasAnyAuthority(Role.ROLE_CAROWNER.name(), Role.ROLE_ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-signup")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login-check")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            HttpSession httpSession = request.getSession();
                            String loggedInUser = "";
                            String htmlPage = "";
                            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER.name()))) {
                                loggedInUser = authentication.getName();
                                htmlPage = "/home-customer";
                            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_CAROWNER.name()))) {
                                loggedInUser = authentication.getName();
                                htmlPage = "/home-carowner";
                            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()))){
                                //TODO admin
                                loggedInUser = authentication.getName();
                                htmlPage = "/home";
                            }

                            httpSession.setAttribute("loggedInUser", loggedInUser);
                            response.sendRedirect(htmlPage);
                        })
                        .failureHandler(authenticationFailureHandler())

                );

        return http.build();
    }

    // Handler fail authentication
    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            String errorParam = "";
            if (exception instanceof BadCredentialsException) {
                errorParam = "?error=invalidCredentials";
            } else if (exception instanceof DisabledException) {
                errorParam = "?error=accountDisabled";
            } else if (exception instanceof LockedException) {
                errorParam = "?error=accountLocked";
            }

            String username = request.getParameter("email");
            response.sendRedirect("/login-signup" + errorParam + "&email=" + username);
        };
    }

}
