package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(long id);
    List<Task> findByHorasGreaterThan(double horas);
    Page<Task> findByProjectNameAndCreationDateBetweenOrderByCreationDateDesc(String filtro, Pageable pageable, Date inicio, Date fin);
    Page<Task> findByProjectNameOrderByCreationDateDesc(String projectName, Pageable pageable);

}
