package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Repository.ProyectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectService {
    @Autowired
    private ProyectRepository proyectRepository;
    public Proyect GuardarProyect(Proyect proyect){
        return proyectRepository.save(proyect);
    }
    public List<Proyect> obtenerProyects() {
        return proyectRepository.findAll();
    }

}
