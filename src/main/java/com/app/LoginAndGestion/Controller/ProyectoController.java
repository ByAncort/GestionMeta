package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.DTO.TaskDTO;
import com.app.LoginAndGestion.DTO.TaskLineDTO;
import com.app.LoginAndGestion.DTO.TaskWithResponsableDTO;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.TaskLine;
import com.app.LoginAndGestion.Model.User;
import com.app.LoginAndGestion.Repository.KanbanRepository;
import com.app.LoginAndGestion.Repository.ProyectRepository;
import com.app.LoginAndGestion.Repository.TaskRepository;
import com.app.LoginAndGestion.Repository.TypeTaskRepository;
import com.app.LoginAndGestion.Service.ProyectService;
import com.app.LoginAndGestion.Service.TaskService;
import com.app.LoginAndGestion.Service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    private final TaskService taskService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TypeTaskRepository typeTaskRepository;
    @Autowired
    private ProyectService proyectService;
    @Autowired
    private ProyectRepository proyectRepository;
    @Autowired
    private KanbanRepository kanbanRepository;

    public ProyectoController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/{filtro}/edit/{id}")
    public String editporId(@PathVariable String filtro, @PathVariable long id, Model model) {
        Optional<TaskDTO> taskOpt = taskService.findID(id);
        List<User> users = userLoginService.listUser();

        if (taskOpt.isPresent()) {
            TaskDTO task = taskOpt.get();
            int totalHoras = 0;
            for (TaskLineDTO taskLineDTO : task.getTaskLines()) {
                double hours = taskLineDTO.getHours();
                totalHoras += hours;
            }

            model.addAttribute("users", users);
            model.addAttribute("taskforEdit", task);
            model.addAttribute("kanbanType" ,kanbanRepository.findAll());
            model.addAttribute("filtrodos", filtro);
            model.addAttribute("totalHoras",totalHoras);
            return "taskDatail";

        } else {

            return "redirect:/{filtro}";
        }
    }


    //    traer las task del proyecto seleccionado
    @GetMapping("/{ProjectName}")
    public String index(
                        @PathVariable String ProjectName,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false)
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date comienzo,
                        @RequestParam(required = false)
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin,
                        @RequestParam(required = false) String responsable,
                        @RequestParam(required = false) String estado,
                        Model model) {
        // Obtener la lista de tareas filtradas
        Page<TaskDTO> taskPage;
        if ("Seleccione un responsable".equals(responsable)){
            responsable=null;
        }
        if ("Seleccione un estado".equals(estado)){
            estado=null;
        }
//       (String projectName, String responsable, Pageable pageable, Date startDate, Date endDate, String status)

        taskPage = taskService.FilterFindTask(ProjectName, responsable, PageRequest.of(page, size), comienzo, fin, estado);

        List<User> users = userLoginService.listUser();

        model.addAttribute("users", users);
        model.addAttribute("filtro", ProjectName);
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("kanbanType" ,kanbanRepository.findAll());
        model.addAttribute("tasktypes", typeTaskRepository.findAll());
        model.addAttribute("currentPage", taskPage.getNumber());
        model.addAttribute("totalPages", taskPage.getTotalPages());
        return "index";
    }

    @GetMapping("/all")
    public String AllTask(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false)
                        @DateTimeFormat(pattern = "dd-mm-yyyy") Date comienzo,
                        @RequestParam(required = false)
                        @DateTimeFormat(pattern = "dd-mm-yyyy") Date fin,
                        @RequestParam(required = false) String responsable,
                        @RequestParam(required = false) String estado,
                        Model model) {
        // Obtener la lista de tareas filtradas
        Page<TaskDTO> taskPage;

        if ("Seleccione un responsable".equals(responsable)){
            responsable=null;
        }
        if ("Seleccione un estado".equals(estado)){
            estado=null;
        }
        taskPage = taskService.FilterFindTaskAllproyect(responsable, PageRequest.of(page, size), comienzo, fin,estado);

        List<User> users = userLoginService.listUser();

        model.addAttribute("users", users);
        model.addAttribute("filtro", "todas las Task");
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("kanbanType" ,kanbanRepository.findAll());
        model.addAttribute("proyects",proyectRepository.findAll());
        model.addAttribute("tasktypes", typeTaskRepository.findAll());
        model.addAttribute("currentPage", taskPage.getNumber());
        model.addAttribute("totalPages", taskPage.getTotalPages());
        return "index";
    }

    @GetMapping("/users")
    public String usertask(Model model){
        List<TaskWithResponsableDTO> tasks = taskRepository.findAllWithResponsables();

        model.addAttribute("tasks", tasks);
        model.addAttribute("users", userLoginService.listUser());
        return "taskForUser";
    }
}
