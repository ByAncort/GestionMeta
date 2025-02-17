package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.DTO.TaskRequestDTO;
import com.app.LoginAndGestion.Model.*;
import com.app.LoginAndGestion.Repository.TaskRepository;
import com.app.LoginAndGestion.Repository.TypeTaskRepository;
import com.app.LoginAndGestion.Repository.UserRepository;
import com.app.LoginAndGestion.Service.ProyectService;
import com.app.LoginAndGestion.Service.RolService;
import com.app.LoginAndGestion.Service.TaskService;
import com.app.LoginAndGestion.Service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class RestController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProyectService proyectService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolService rolService;
    @Autowired
    private TypeTaskRepository typeTaskRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserLoginService userLoginService;

    @PostMapping(value = "/api/task", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        try {

            Task savedTask = taskService.saveTaskFromDTO(taskRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping(value = "/api/proyect/add", consumes = "application/json", produces = "application/json")
    public Proyect createUser(@RequestBody Map<String, String> requestData) {
        String name = requestData.get("name");
        String lastName = requestData.get("lastName");

        Proyect nuevoProyecto=new Proyect();
        nuevoProyecto.setName(name);
        nuevoProyecto.setLast_name(lastName);
        return proyectService.GuardarProyect(nuevoProyecto);
    }

    @PostMapping(value = "/api/role/add", consumes = "application/json", produces = "application/json")
    public Role registrarRole(@RequestBody Role role){return rolService.saveRole(role);}
    @PostMapping("/{userId}/role/{roleId}")
    public ResponseEntity<?> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        try {
            userLoginService.assignRoleToUser(userId, roleId);
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "Rol asignado correctamente");
            }});
        }catch (Exception e){
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", e.getMessage());
            }});
        }

    }




    @PostMapping(value = "/task/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Task updatedTask = taskService.UpdateStep(id);
            return ResponseEntity.ok("Tarea actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }



    @PostMapping(value = "/update/task/{id}")
    public ResponseEntity<String> updateT(@PathVariable long id, @RequestBody Task tareaActualizada) {
        try {
            taskService.updateTask(id, tareaActualizada);
            return ResponseEntity.ok("Tarea actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/update-task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable long id, @RequestBody Map<String, Object> task){
        taskService.UpdateTask(id, task);
        return ResponseEntity.ok("Tarea actualizada correctamente");
    }
    @PostMapping("/add/add-task-line/{idtask}")
    public ResponseEntity<?> addTaskLine(@PathVariable long idtask, @RequestBody Map<String, Object> taskLine){
       taskService.addTaskLine(idtask,taskLine);
       return ResponseEntity.ok("Subtarea agregada correctamente");

    }

    @PostMapping(value = "/updateTaskStatus/{idTarea}/{status}")
    public ResponseEntity<?> getTask(@PathVariable long idTarea, @PathVariable String status) {
        taskService.updateStepTask(idTarea, status);
        return ResponseEntity.ok("Tarea actualizada correctamente");
    }



}
