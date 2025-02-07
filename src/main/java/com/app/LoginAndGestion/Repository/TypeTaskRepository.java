package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.TypeTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeTaskRepository extends JpaRepository<TypeTask, Long> {

}
