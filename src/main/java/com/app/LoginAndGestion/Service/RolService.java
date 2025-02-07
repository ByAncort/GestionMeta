package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Repository.RolRepository;
import com.app.LoginAndGestion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Role> obtenerRoles() {
        return rolRepository.findAll();
    }
    public Role saveRole(Role role){return rolRepository.save(role);}

}
