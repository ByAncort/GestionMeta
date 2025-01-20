package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.UserLogin;
import com.app.LoginAndGestion.Service.ProyectService;
import com.app.LoginAndGestion.Service.TaskService;
import com.app.LoginAndGestion.Service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    private final TaskService taskService;
    @Autowired
    private UserLoginService userLoginService;

    public ProyectoController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{filtro}/edit/{id}")
    public String editporId(@PathVariable String filtro, @PathVariable long id, Model model) {
        Optional<Task> taskOpt = taskService.findID(id);
        List<UserLogin> userLogins = userLoginService.listUser ();

        if (taskOpt.isPresent()) {

            Task task = taskOpt.get();
            model.addAttribute("users", userLogins);
            model.addAttribute("taskforEdit", task);
            model.addAttribute("filtrodos", filtro);
            return "Detailtask";

        } else {

            return "redirect:/{filtro}";
        }
    }

    @GetMapping("/{filtro}")
    public String index(@PathVariable String filtro,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false)
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date comienzo,
                        @RequestParam(required = false)
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin,
                        Model model) {
        // Obtener la lista de tareas filtradas
        Page<Task> taskPage;
        if(comienzo != null && fin != null) {
            taskPage = taskService.FiltroProyetBetween(filtro, PageRequest.of(page, size), comienzo, fin);
        } else {
            taskPage = taskService.FiltroXproyet(filtro, PageRequest.of(page, size));
        }

        List<UserLogin> userLogins = userLoginService.listUser();

        model.addAttribute("users", userLogins);
        model.addAttribute("filtro", filtro);
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("currentPage", taskPage.getNumber());
        model.addAttribute("totalPages", taskPage.getTotalPages());
        return "index";
    }




}
