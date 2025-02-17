package com.app.LoginAndGestion.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "task_line")
public class TaskLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String comment;
    private Double horasTask;


    public TaskLine(long id, Task task, String comment, Double horasTask) {
        this.id = id;
        this.task = task;
        this.comment = comment;
        this.horasTask = horasTask;
    }

    public TaskLine() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getHorasTask() {
        return horasTask;
    }

    public void setHorasTask(Double horasTask) {
        this.horasTask = horasTask;
    }
}
