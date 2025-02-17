package com.app.LoginAndGestion.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    private String status;
    private double horas;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "type_task_id")
    private TypeTask type;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<TaskLine> taskLines = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_login_id")
    )
    private Set<User> responsables = new HashSet<>();

    public Set<TaskLine> getTaskLines() {
        return taskLines;
    }

    public void setTaskLines(Set<TaskLine> taskLines) {
        this.taskLines = taskLines;
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

    public TypeTask getType() {
        return type;
    }

    public void setType(TypeTask type) {
        this.type = type;
    }

    public Set<User> getResponsables() {
        return responsables;
    }

    public void setResponsables(Set<User> nuevoResponsables) {
        this.responsables.clear();
        if (nuevoResponsables!=null){
            this.responsables.addAll(nuevoResponsables);
        }
    }
}
