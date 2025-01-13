package com.app.LoginAndGestion.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUsername(String username);
}
