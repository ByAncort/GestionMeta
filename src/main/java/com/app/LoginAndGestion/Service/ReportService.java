package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Repository.ProyectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ProyectRepository proyectRepository;


    public void savexcel(String IdProyect,long rango){
        if(IdProyect.toString()== "all"){
            List<Proyect> proyects = proyectRepository.findAll();

        }else {
            Long id = Long.parseLong(IdProyect);
            Proyect proyect=proyectRepository.findAllById(id);

        }

    }


}
