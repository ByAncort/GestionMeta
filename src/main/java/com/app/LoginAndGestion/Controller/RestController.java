package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Service.ProyectService;
import com.app.LoginAndGestion.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProyectService proyectService;

    @PostMapping(value = "/api/task", consumes = "application/json", produces = "application/json")
    public Task createUser(@RequestBody Task task) {
        return taskService.guardarTarea(task);
    }
    @PostMapping(value = "/api/proyect/add", consumes = "application/json", produces = "application/json")
    public Proyect createUser(@RequestBody Proyect proyect) {
        return proyectService.GuardarProyect(proyect);
    }

    @PostMapping(value = "/api/task/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Task> updateTask(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Task updatedTask = taskService.UpdateStep(id);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
