package com.app.LoginAndGestion.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "kanban_type")
public class kanbanType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public kanbanType( String name) {

        this.name = name;
    }

    public kanbanType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
