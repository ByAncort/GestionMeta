package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.Model.Proyect;
import com.app.LoginAndGestion.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectRepository extends JpaRepository<Proyect, Long> {

}
