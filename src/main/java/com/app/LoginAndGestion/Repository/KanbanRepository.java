package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.Model.kanbanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KanbanRepository extends JpaRepository<kanbanType, Long> {
}
