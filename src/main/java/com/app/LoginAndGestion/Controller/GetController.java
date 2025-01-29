package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Repository.ProyectRepository;
import com.app.LoginAndGestion.Repository.TaskRepository;
import com.app.LoginAndGestion.Service.ProyectService;
import com.app.LoginAndGestion.Service.RolService;
import com.app.LoginAndGestion.Service.TaskService;
import com.app.LoginAndGestion.Service.UserLoginService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@AllArgsConstructor
public class GetController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProyectService proyectService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private RolService rolService;
    @Autowired
    private ProyectRepository proyectRepository;
    @GetMapping({"/", "/proyectos"})
    public String proyect(Model model){
        model.addAttribute("proyects", proyectService.obtenerProyects());
        return "proyect";
    }
    @GetMapping("/report")
    public String report(Model model){
        model.addAttribute("proyects", proyectService.obtenerProyects());
        return "report";
    }

    @GetMapping("/kanban")
    public String kanvan(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        model.addAttribute("tasks" ,taskRepository.findAllWithResponsables());
        model.addAttribute("user" , userDetails);
        return "kanban";
    }


    @GetMapping("/gestion/roles")
    public String roles(Model model){
        List<Role> roles = rolService.obtenerRoles();
        model.addAttribute("users",userLoginService.listUser());
        model.addAttribute("roles", roles);
        return "roles";
    }
}
