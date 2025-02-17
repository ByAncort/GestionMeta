package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.DTO.TaskDTO;
import com.app.LoginAndGestion.DTO.TaskLineDTO;
import com.app.LoginAndGestion.DTO.TaskRequestDTO;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.TaskLine;
import com.app.LoginAndGestion.Model.TypeTask;
import com.app.LoginAndGestion.Model.User;
import com.app.LoginAndGestion.Repository.TaskRepository;
import com.app.LoginAndGestion.Repository.TypeTaskRepository;
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
    @Autowired
    private TypeTaskRepository typeTaskRepository;


    public List<Task> getTasksByProjectAndDateRange(String projectName, Date startDate, Date endDate) {
        return taskRepository.findAllByCreationDateBetweenAndProjectName(startDate, endDate, projectName);
    }

    public Optional<TaskDTO> findID(long id) {
        return taskRepository.findById(id).map(TaskDTO::new);
    }


    public Task guardarTareaConResponsables(Task tarea, List<Long> responsablesIds) {
        Task tareaGuardada = taskRepository.save(tarea);
        List<User> responsables = userRepository.findAllById(responsablesIds);
        System.out.println("Responsables: " + responsables);
        tareaGuardada.setResponsables(new HashSet<>(responsables));
        return taskRepository.save(tareaGuardada);
    }

    public Page<TaskDTO> FilterFindTask(String projectName, String responsable, Pageable pageable, Date startDate, Date endDate, String status) {
        return taskRepository.findAllByFilters(projectName,responsable,pageable, startDate, endDate, status)
                .map(TaskDTO::new);
    }
    public Page<TaskDTO> FilterFindTaskAllproyect(String responsable, Pageable pageable, Date startDate, Date endDate, String status) {

        return taskRepository.findAllByFiltersAllProyect(responsable, startDate, endDate, status, pageable)
                .map(TaskDTO::new);
    }

    public Page<TaskDTO> FilterFindTaskByproyect(String ProjectName,String responsable, Pageable pageable, Date startDate, Date endDate, String status) {

        return taskRepository.findAllByFiltersByProyect(responsable, startDate, endDate, status, ProjectName, pageable)
                .map(TaskDTO::new);
    }

    public Page<TaskDTO> AllTaskResponsable(String responsable, Pageable pageable, Date startDate, Date endDate) {
        if (responsable == null && startDate == null && endDate == null) {
            return taskRepository.findAll(pageable)
                    .map(TaskDTO::new);
        }

        if (responsable != null && startDate == null && endDate == null) {
            return taskRepository.findByResponsables(responsable, pageable)
                    .map(TaskDTO::new);
        }


        if (responsable != null && startDate != null && endDate != null) {
            return taskRepository.findByResponsablesAndCreationDateBetween(responsable, startDate, endDate, pageable)
                    .map(TaskDTO::new);
        }


        if (responsable == null && startDate != null && endDate != null) {
            return taskRepository.findByCreationDateBetween(startDate, endDate, pageable)
                    .map(TaskDTO::new);
        }

        return taskRepository.findAll(pageable)
                .map(TaskDTO::new);
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

    public TaskLine addTaskLine(Long id,Map<String, Object> taskLine) {
        Optional<Task> t =taskRepository.findById(id);
        if (!t.isPresent()) {
            throw new RuntimeException("Task not found");
        }
        Task task= t.get();
        String subtarea = (String) taskLine.get("subtarea");
        Double horas = Double.valueOf(taskLine.get("horas").toString());

        TaskLine taskLineNew= new TaskLine();
        taskLineNew.setComment(subtarea);
        taskLineNew.setHorasTask(horas);
        taskLineNew.setTask(task);

        task.getTaskLines().add(taskLineNew);
        taskRepository.save(task);
        System.out.println(task.getTaskLines().toString());
        return taskLineNew;

    }


    public Task UpdateTask(Long idTask, Map<String, Object>  tarea ){
        Date date = new Date();
        Optional<Task> optionalTask =taskRepository.findById(idTask);
        Task TareaActual =optionalTask.get();
        List<Integer> idTareas = new ArrayList<>();
        if(tarea.containsKey("responsable")) {
            Object responsableObj = tarea.get("responsable");
            for (Object id : (List<?>) responsableObj) {
                idTareas.add(Integer.parseInt(id.toString()));
            }
        }



        if(tarea.containsKey("categorias")) {
            Object categoriaOBj = tarea.get("categorias");
            System.out.println(categoriaOBj);
        }

        if(idTareas!= null ) {
            Set<User> responsablesActualizados = new HashSet<>();
            for (Integer ids : idTareas) {
                Optional<User> exi= userRepository.findById(Long.valueOf(ids));
                exi.ifPresent(responsablesActualizados::add);
            }
            TareaActual.setResponsables(responsablesActualizados);
        }


        TareaActual.setLastUpdateDate(date);
        TareaActual.setDescription((String) tarea.get("description"));
        TareaActual.setName((String) tarea.get("name"));
        TareaActual.setStatus((String) tarea.get("status"));

        return taskRepository.save(TareaActual);

    }

    public Task updateTask(Long id, Task tareaActualizada) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            throw new RuntimeException("Tarea no encontrada con el ID: " + id);
        }

        Task tareaExistente = optionalTask.get();
        tareaExistente.setName(tareaActualizada.getName());
        tareaExistente.setDescription(tareaActualizada.getDescription());
        tareaExistente.setStatus(tareaActualizada.getStatus());
        tareaExistente.setProjectName(tareaActualizada.getProjectName());
        tareaExistente.setHoras(tareaActualizada.getHoras());
        tareaExistente.setResponsables(tareaActualizada.getResponsables());
        return taskRepository.save(tareaExistente);
    }

    public Task UpdateStep(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            double totalHoras = 0;

            if (task.getTaskLines() != null) {
                for (TaskLine taskLine : task.getTaskLines()) {
                    if (taskLine != null && taskLine.getHorasTask() != null) {
                        totalHoras += taskLine.getHorasTask();
                    }
                }
            }

            if (totalHoras <= 0) {
                throw new RuntimeException("Horas deben ser mayores a 0 para completar la tarea" );
            }

            task.setStatus("Completada");

            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with ID: " + taskId);
        }
    }
    public Task updateStepTask(long id, String step){
        Optional<Task> find= taskRepository.findById(id);
        Task tarea = find.get();
        tarea.setStatus(step);
        return taskRepository.save(tarea);
    }
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


    public Task saveTaskFromDTO(TaskRequestDTO taskDTO) {
        Date date = new Date();
        TypeTask typeTask = typeTaskRepository.findById(taskDTO.getType())
                .orElseThrow(() -> new RuntimeException("TypeTask no encontrado"));

        // Creamos una nueva tarea
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setStatus(taskDTO.getStatus());
        task.setHoras(taskDTO.getHoras());
        task.setCreationDate(date);
        task.setDescription(taskDTO.getDescription());
        task.setProjectName(taskDTO.getProjectName());
        task.setType(typeTask);

        // Asignamos responsables si los IDs son proporcionados
        if (taskDTO.getResponsablesIds() != null && !taskDTO.getResponsablesIds().isEmpty()) {
            Set<User> responsables = new HashSet<>();
            for (Long responsableId : taskDTO.getResponsablesIds()) {
                User responsable = userRepository.findById(responsableId)
                        .orElseThrow(() -> new RuntimeException("Responsable no encontrado"));
                responsables.add(responsable);
            }
            task.setResponsables(responsables);
        }

        // Guardamos la tarea
        return taskRepository.save(task);
    }

}


