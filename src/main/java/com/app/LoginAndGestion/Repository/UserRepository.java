package com.app.LoginAndGestion.Repository;

import com.app.LoginAndGestion.Model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUsername(String username);
}
