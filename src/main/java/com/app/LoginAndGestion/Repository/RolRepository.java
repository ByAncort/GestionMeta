package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.Model.Role;
import com.app.LoginAndGestion.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT * FROM role", nativeQuery = true)
    List<Role> findAll();
}
