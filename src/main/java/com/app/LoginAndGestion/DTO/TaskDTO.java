package com.app.LoginAndGestion.DTO;

import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.TaskLine;
import com.app.LoginAndGestion.Model.TypeTask;
import com.app.LoginAndGestion.Model.User;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskDTO {
    private Long id;
    private String name;
    private Date creationDate;
    private Date lastUpdateDate;
    private String status;
    private double horas;
    private String description;
    private String projectName;
    private String type;
    private Set<User> responsables;
    private Set<String> mails;
    private Set<TaskLineDTO> taskLines;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.creationDate = task.getCreationDate();
        this.lastUpdateDate = task.getLastUpdateDate();
        this.status = task.getStatus();
        this.horas = task.getHoras();
        this.description = task.getDescription();
        this.projectName = task.getProjectName();
//        this.responsables = task.getResponsables().stream()
//                .map(User::getUsername)
//                .collect(Collectors.toSet());
        this.responsables = task.getResponsables(); // Esto ya es un Set<User>
        this.mails = task.getResponsables().stream()
                .map(User::getEmail)
                .collect(Collectors.toSet());
        this.type = (task.getType() != null) ? task.getType().getName() : "Desconocido";
        this.taskLines = task.getTaskLines().stream()
                .map(TaskLineDTO::new)
                .collect(Collectors.toSet());

    }

    public Set<TaskLineDTO> getTaskLines() {
        return taskLines;
    }

    public void setTaskLines(Set<TaskLineDTO> taskLines) {
        this.taskLines = taskLines;
    }

    public Set<String> getMails() {
        return mails;
    }

    public void setMails(Set<String> mails) {
        this.mails = mails;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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

    public Set<User> getResponsables() {
        return responsables;
    }

    public void setResponsables(Set<User> responsables) {
        this.responsables = responsables;
    }
}
