package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    public List<Role> obtenerRoles() {
        return rolRepository.findAll();
    }
}
