package com.app.LoginAndGestion.config;

import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.TypeTask;
import com.app.LoginAndGestion.Model.kanbanType;
import com.app.LoginAndGestion.Repository.RolRepository;
import com.app.LoginAndGestion.Repository.TypeTaskRepository;
import com.app.LoginAndGestion.Repository.KanbanRepository;
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
    public CommandLineRunner loadTypeKanban(KanbanRepository kanbanRepository){
        return  args -> {
            if (kanbanRepository.findAll().isEmpty()){
                kanbanRepository.save(new kanbanType("Por hacer"));
                kanbanRepository.save(new kanbanType("En progreso"));
                kanbanRepository.save(new kanbanType("Revision"));
                kanbanRepository.save(new kanbanType("Completada"));
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
