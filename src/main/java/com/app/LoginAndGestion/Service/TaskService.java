package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Optional<Task> findID(long id) {
        return taskRepository.findById(id);
    }

        public Task guardarTarea(Task tarea) {
        return taskRepository.save(tarea);
    }

    public List<Task> obtenerTareas() {
        return taskRepository.findAll();
    }
    public Page<Task> FiltroProyetBetween(String projectName, Pageable pageable, Date startDate, Date endDate) {
        // Aquí va la lógica de filtrado utilizando el rango de fechas
        return taskRepository.findByProjectNameAndCreationDateBetweenOrderByCreationDateDesc(projectName, pageable, startDate, endDate);
    }

    public Page<Task> FiltroXproyet(String projectName, Pageable pageable) {
        // Lógica para filtrar solo por nombre de proyecto sin fechas
        return taskRepository.findByProjectNameOrderByCreationDateDesc(projectName, pageable);
    }


    public Task updateTask(Long id, Task tareaActualizada) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            throw new RuntimeException("Tarea no encontrada con el ID: " + id);
        }

        Task tareaExistente = optionalTask.get();

        // Actualizamos los campos necesarios
        tareaExistente.setName(tareaActualizada.getName());
        tareaExistente.setDescription(tareaActualizada.getDescription());
        tareaExistente.setResponsable(tareaActualizada.getResponsable());
        tareaExistente.setStatus(tareaActualizada.getStatus());
        tareaExistente.setProjectName(tareaActualizada.getProjectName());

        // Guardamos los cambios
        return taskRepository.save(tareaExistente);
    }

    public Task UpdateStep(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            // Obtener la tarea y actualizar el estado
            Task task = optionalTask.get();
            task.setStatus("Completada");
            return taskRepository.save(task); // Guardar los cambios en la base de datos
        } else {
            throw new RuntimeException("Task not found with ID: " + taskId);
        }
    }
}


