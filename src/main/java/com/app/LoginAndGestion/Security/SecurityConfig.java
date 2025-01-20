package com.app.LoginAndGestion.Security;

import com.app.LoginAndGestion.Service.UserLoginService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserLoginService appUserService;

    @Autowired
    public SecurityConfig(UserLoginService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Transactional
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm -> {
                    httpForm
                            .loginPage("/login")
                            .permitAll();
                    httpForm.defaultSuccessUrl("/proyectos", true);  // Asegúrate de que esté configurado correctamente
                })
                .authorizeHttpRequests(registry -> {
                    // Configurar rutas específicas antes de `anyRequest()`
                    registry.requestMatchers("/api/task/update").hasRole("ADMIN");
                    registry.requestMatchers("/update/**").hasRole("ADMIN");

                    // Rutas públicas
                    registry.requestMatchers("/signup", "/proyectos/**", "/css/**", "/js/**").permitAll();

                    // Regla global para cualquier otra solicitud
                    registry.anyRequest().authenticated();
                })
                .build();
    }

}
