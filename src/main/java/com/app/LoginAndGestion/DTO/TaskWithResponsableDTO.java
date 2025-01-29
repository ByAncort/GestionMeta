package com.app.LoginAndGestion.DTO;

import java.util.Date;

public class TaskWithResponsableDTO {
    private Long id;
    private Date creationDate;
    private String description;
    private double horas;
    private Date lastUpdateDate;
    private String name;
    private String projectName;
    private String status;
    private String username;

    public TaskWithResponsableDTO(Long id, Date creationDate, String description, double horas,
                                  Date lastUpdateDate, String name, String projectName,
                                  String status, String username) {
        this.id = id;
        this.creationDate = creationDate;
        this.description = description;
        this.horas = horas;
        this.lastUpdateDate = lastUpdateDate;
        this.name = name;
        this.projectName = projectName;
        this.status = status;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHoras() {
        return horas;
    }

    public void setHoras(double horas) {
        this.horas = horas;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
