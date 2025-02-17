package com.app.LoginAndGestion.DTO;

import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.TaskLine;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

public class TaskLineDTO {

    private Long id;
    private String description;
    private double hours;
    private Long taskId;

    public TaskLineDTO() {}

    public TaskLineDTO(TaskLine taskLine) {
        this.id = taskLine.getId();
        this.description = taskLine.getComment();
        this.hours = taskLine.getHorasTask();
        this.taskId = (taskLine.getTask() != null) ? taskLine.getTask().getId() : null;
    }

    public TaskLineDTO(Long id, String description, double hours, Long taskId) {
        this.id = id;
        this.description = description;
        this.hours = hours;
        this.taskId = taskId;
    }

    public TaskLineDTO(Task task) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    // Métodos equals y hashCode para comparar objetos TaskLineDTO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskLineDTO that = (TaskLineDTO) o;
        return Double.compare(that.hours, hours) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, hours, taskId);
    }

    // Método toString para representación en texto
    @Override
    public String toString() {
        return "TaskLineDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", taskId=" + taskId +
                '}';
    }
}
