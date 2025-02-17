package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.DTO.TaskDTO;
import com.app.LoginAndGestion.DTO.TaskWithResponsableDTO;
import com.app.LoginAndGestion.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(long id);
//    List<Task> findAllByProjectName(String name);
    Page<Task> findAllByProjectName(String name,Pageable pageable);
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

    @Query("SELECT new com.app.LoginAndGestion.DTO.TaskWithResponsableDTO(" +
            "t.id, t.creationDate, t.description, t.horas, t.lastUpdateDate, " +
            "t.name, t.projectName, t.status, u.username) " +
            "FROM Task t " +
            "JOIN t.responsables u " +
            "WHERE u.username = :username"
    )
    Page<TaskWithResponsableDTO> findByUsermane(@Param("username") String responsables, Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "JOIN t.responsables r " +
            "WHERE (:project IS NULL OR t.projectName like :project)  " +
            "AND (:responsable IS NULL OR r.username = :responsable) " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:inicio IS NULL OR t.creationDate >= :inicio) " +
            "AND (:fin IS NULL OR t.creationDate <= :fin) ")
    Page<Task> findAllByFilters(
            @Param("project") String projectName,
            @Param("responsable") String responsable,
            Pageable pageable,
            @Param("inicio") Date startDate,
            @Param("fin") Date endDate,
            @Param("status") String status);


    @Query("SELECT t FROM Task t " +
            "JOIN t.responsables r " +
            "WHERE (:responsable IS NULL OR r.username = :responsable)  " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:inicio IS NULL OR t.creationDate <= :inicio) " +
            "AND (:fin IS NULL OR t.creationDate <= :fin) " +
            "AND (:proyect IS NULL OR t.projectName <= :proyect) " +
            "order by t.creationDate desc")
    Page<Task> findAllByFiltersByProyect(
            @Param("responsable") String responsable,
            @Param("inicio") Date startDate,
            @Param("fin") Date endDate,
            @Param("status") String status,
            @Param("proyect") String ProyectName,
            Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "JOIN t.responsables r " +
            "WHERE (:responsable IS NULL OR r.username = :responsable)  " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:inicio IS NULL OR t.creationDate <= :inicio) " +
            "AND (:fin IS NULL OR t.creationDate <= :fin) " +
            "order by t.creationDate desc")
    Page<Task> findAllByFiltersAllProyect(
            @Param("responsable") String responsable,
            @Param("inicio") Date startDate,
            @Param("fin") Date endDate,
            @Param("status") String status,
            Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "JOIN t.responsables r " +
            "WHERE (:project IS NULL OR t.projectName = :project) " +
            "AND (:inicio IS NULL OR t.creationDate <= :inicio) " +
            "AND (:fin IS NULL OR t.creationDate <= :fin) " +
            "ORDER BY t.creationDate DESC")
    List<Task> findAllForDocument(
            @Param("project") String project,
            @Param("inicio") Date startDate,
            @Param("fin") Date endDate);


}
