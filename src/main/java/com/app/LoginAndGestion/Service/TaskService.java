package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.UserLogin;
import com.app.LoginAndGestion.Repository.TaskRepository;
import com.app.LoginAndGestion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    public List<Task> getTasksByProjectAndDateRange(String projectName, Date startDate, Date endDate) {
        return taskRepository.findAllByCreationDateBetweenAndProjectName(startDate, endDate, projectName);
    }

    public Optional<Task> findID(long id) {
        return taskRepository.findById(id);
    }

    public Task guardarTareaConResponsables(Task tarea, List<Long> responsablesIds) {
        Task tareaGuardada = taskRepository.save(tarea);
        List<UserLogin> responsables = userRepository.findAllById(responsablesIds);
        System.out.println("Responsables: " + responsables);
        tareaGuardada.setResponsables(new HashSet<>(responsables));
        return taskRepository.save(tareaGuardada);
    }

//  Buscar task por proyecto dentro de unas fechas
    public Page<Task> AllTaskFilter(String projectName, Pageable pageable, Date startDate, Date endDate) {
        if(startDate == null && endDate == null){
            return taskRepository.findByProjectNameOrderByCreationDateDesc(projectName, pageable);
        } else if (startDate!= null && endDate!=null){
            return taskRepository.findByProjectNameAndCreationDateBetweenOrderByCreationDateDesc(projectName, pageable, startDate, endDate);
        }
        return taskRepository.findAll(pageable);
    }

    public Page<Task> AllTaskResponsable(String responsable, Pageable pageable, Date startDate, Date endDate) {
        if (responsable == null && startDate == null && endDate == null) {
            return taskRepository.findAll(pageable);
        }

        if (responsable != null && startDate == null && endDate == null) {
            return taskRepository.findByResponsables(responsable, pageable);
        }


        if (responsable != null && startDate != null && endDate != null) {
            return taskRepository.findByResponsablesAndCreationDateBetween(responsable, startDate, endDate, pageable);
        }


        if (responsable == null && startDate != null && endDate != null) {
            return taskRepository.findByCreationDateBetween(startDate, endDate, pageable);
        }

        return taskRepository.findAll(pageable);
    }


    public Page<Task> AllTaskResponsablev2(String responsable, Pageable pageable, Date startDate, Date endDate) {
        if (responsable == null && startDate == null && endDate == null) {
            return taskRepository.findAll(pageable);
        }

        if (responsable != null && startDate == null && endDate == null) {
            return taskRepository.findByResponsables(responsable, pageable);
        }


        if (responsable != null && startDate != null && endDate != null) {
            return taskRepository.findByResponsablesAndCreationDateBetween(responsable, startDate, endDate, pageable);
        }


        if (responsable == null && startDate != null && endDate != null) {
            return taskRepository.findByCreationDateBetween(startDate, endDate, pageable);
        }

        return taskRepository.findAll(pageable);
    }


//  Buscar task por proyect
    public Page<Task> FiltroXproyet(String projectName, Pageable pageable) {
        return taskRepository.findByProjectNameOrderByCreationDateDesc(projectName, pageable);
    }
//  Buscar todas las task
    public Page<Task> AllTaskProyect(Pageable pageable){
        return taskRepository.findAll(pageable);
    }
    //  Buscar todas las task
    public Page<Task> AllTaskProyectResponsable(String responsable,Pageable pageable){
        return taskRepository.findByResponsables(responsable,pageable);
    }
//  buscar todas las task por rango de fecha
    public Page<Task> AllTaskDatesBeteew(Pageable pageable,Date startDate, Date endDate){
        return AllTaskDatesBeteew(pageable,startDate,endDate);
    }


    public Task updateTask(Long id, Task tareaActualizada) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            throw new RuntimeException("Tarea no encontrada con el ID: " + id);
        }

        Task tareaExistente = optionalTask.get();

        tareaExistente.setName(tareaActualizada.getName());
        tareaExistente.setDescription(tareaActualizada.getDescription());
        tareaExistente.setResponsables(tareaActualizada.getResponsables());
        tareaExistente.setStatus(tareaActualizada.getStatus());
        tareaExistente.setProjectName(tareaActualizada.getProjectName());

        return taskRepository.save(tareaExistente);
    }

    public Task UpdateStep(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            if (optionalTask.get().getHoras()>0) {
                Task task = optionalTask.get();
                task.setStatus("Completada");
                return taskRepository.save(task);
            }
            throw new RuntimeException("Horas menores a 0: " + taskId);
        } else {
            throw new RuntimeException("Task not found with ID: " + taskId);
        }
    }
    public List<Task> buscarPorNombre(String name){return taskRepository.findAllByProjectName(name);}

    public List<Task> getTasksByDateRangeAndProjectName(String name, String rango) {
        Date currentDate = new Date();
        Date startDate;
        Date endDate = currentDate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        switch (rango) {
            case "2":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case "3":
                calendar.set(Calendar.DAY_OF_MONTH, -3); // Últimos 3 días
                break;
            case "4":
                calendar.set(Calendar.DAY_OF_MONTH, -6); // Últimos 6 días
                break;
            default:
                calendar.set(Calendar.DAY_OF_MONTH, -2000);
                break;
        }

        startDate = calendar.getTime();

        if ("all".equals(name)) {
            return taskRepository.findAllByCreationDateBetween(startDate, endDate);
        } else {
            return taskRepository.findAllByCreationDateBetweenAndProjectName(startDate, endDate, name);
        }
    }
}


