package com.app.LoginAndGestion.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String roleName;  // Este es el nombre del rol, como "USER", "ADMIN", etc.

    // Constructor vacío para JPA
    public Role() {}

    // Constructor con parámetros
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
