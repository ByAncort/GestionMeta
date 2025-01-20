package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.UserLogin;
import com.app.LoginAndGestion.Service.ProyectService;
import com.app.LoginAndGestion.Service.RolService;
import com.app.LoginAndGestion.Service.TaskService;
import com.app.LoginAndGestion.Service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProyectService proyectService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private RolService rolService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @GetMapping("/dashboard")
    public String index(Model model){
        List<Task> tasks = taskService.obtenerTareas();
        model.addAttribute("tasks", tasks);
        return "index";
    }


    @GetMapping("/proyectos")
    public String proyect(Model model){
        List<Proyect> proyects =proyectService.obtenerProyects();
        model.addAttribute("proyects", proyects);
        return "proyect";
    }
    @GetMapping("/GestionRoles")
    public String roles(Model model){

        model.addAttribute("users",userLoginService.listUser());
        model.addAttribute("roles", rolService.obtenerRoles());

        return "roles";
    }


}
