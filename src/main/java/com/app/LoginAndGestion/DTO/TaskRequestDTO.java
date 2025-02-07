package com.app.LoginAndGestion.DTO;

import java.util.List;

public class TaskRequestDTO {
    private String name;
    private String status;
    private double horas;
    private String description;
    private String projectName;
    private Long type;  // Aquí tienes el ID del TypeTask
    private List<Long> responsablesIds;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getHoras() {
        return horas;
    }

    public void setHoras(double horas) {
        this.horas = horas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public List<Long> getResponsablesIds() {
        return responsablesIds;
    }

    public void setResponsablesIds(List<Long> responsablesIds) {
        this.responsablesIds = responsablesIds;
    }
}
