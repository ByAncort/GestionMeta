package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.DTO.TaskRequestDTO;
import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Service.ProyectService;
import com.app.LoginAndGestion.Service.RolService;
import com.app.LoginAndGestion.Service.TaskService;
import com.app.LoginAndGestion.Service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProyectService proyectService;
    @Autowired
    private RolService rolService;
    @Autowired
    private UserLoginService userLoginService;

    @PostMapping(value = "/api/task", consumes = "application/json", produces = "application/json")
    public Task createUser(@RequestBody TaskRequestDTO taskRequestDTO) {

        Task task = new Task();

        task.setName(taskRequestDTO.getName());
        task.setStatus(taskRequestDTO.getStatus());
        task.setHoras(taskRequestDTO.getHoras());
        task.setDescription(taskRequestDTO.getDescription());
        task.setProjectName(taskRequestDTO.getProjectName());

        return taskService.guardarTareaConResponsables(task, taskRequestDTO.getResponsablesIds());

    }

    @PostMapping(value = "/api/proyect/add", consumes = "application/json", produces = "application/json")
    public Proyect createUser(@RequestBody Proyect proyect) {
        return proyectService.GuardarProyect(proyect);
    }

    @PostMapping(value = "/api/role/add", consumes = "application/json", produces = "application/json")
    public Role registrarRole(@RequestBody Role role){return rolService.saveRole(role);}
    @PostMapping("/{userId}/role/{roleId}")
    public ResponseEntity<?> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        userLoginService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Rol asignado correctamente");
    }
    @PostMapping(value = "/api/task/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateTask(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        Map<String, String> response = new HashMap<>();

        if (id == null) {
            response.put("errorMessage", "ID is required");
            response.put("errorDescription", "Please provide a valid task ID.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Task updatedTask = taskService.UpdateStep(id);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            response.put("errorMessage", e.getMessage());
            response.put("errorDescription", "The task with the provided ID could not be found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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



}
