package com.app.LoginAndGestion.config;

import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner loadRoles(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.findAll().isEmpty()) {
                rolRepository.save(new Role("ADMIN"));
                rolRepository.save(new Role("USER"));
            }
        };
    }
}
