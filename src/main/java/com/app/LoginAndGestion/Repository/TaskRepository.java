package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.DTO.TaskWithResponsableDTO;
import com.app.LoginAndGestion.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(long id);
    List<Task> findAllByProjectName(String name);
    List<Task> findAllByCreationDateBetween(Date inicio, Date fin);
    List<Task> findAllByCreationDateBetweenAndProjectName(Date startDate, Date endDate, String projectName);
    Page<Task> findByProjectNameAndCreationDateBetweenOrderByCreationDateDesc(String filtro, Pageable pageable, Date inicio, Date fin);
    Page<Task> findByProjectNameOrderByCreationDateDesc(String projectName, Pageable pageable);
    Page<Task> findByResponsables(String responsable, Pageable pageable);
    Page<Task> findByResponsablesAndCreationDateBetween(String responsable, Date startDate, Date endDate, Pageable pageable);
    Page<Task> findByCreationDateBetween(Date startDate, Date endDate, Pageable pageable);
    @Query("SELECT new com.app.LoginAndGestion.DTO.TaskWithResponsableDTO(" +
            "t.id, t.creationDate, t.description, t.horas, t.lastUpdateDate, " +
            "t.name, t.projectName, t.status, u.username) " +
            "FROM Task t " +
            "JOIN t.responsables u")
    List<TaskWithResponsableDTO> findAllWithResponsables();

}
