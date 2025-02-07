package com.app.LoginAndGestion.config;

import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.TypeTask;
import com.app.LoginAndGestion.Repository.RolRepository;
import com.app.LoginAndGestion.Repository.TaskRepository;
import com.app.LoginAndGestion.Repository.TypeTaskRepository;
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
    @Bean
    public  CommandLineRunner loadTypeTask(TypeTaskRepository typeTaskRepository){
        return args -> {
            if (typeTaskRepository.findAll().isEmpty()){
                TypeTask tipoSoporte = new TypeTask("Tecnica");
                TypeTask tipoDesarrollo = new TypeTask("Funcional");
                typeTaskRepository.save(tipoSoporte);
                typeTaskRepository.save(tipoDesarrollo);

            }
        };
    }
}
