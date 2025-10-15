package dto;

import java.io.Serializable;

public class TaskDTO implements Serializable {
    private int taskId;
    private int userId;
    private String title;
    private String description;
    private String dueDate;   // can also use java.sql.Date if you want
    private String priority;  // High, Medium, Low
    private String status;    // Pending, In Progress, Completed

    // ✅ Default Constructor
    public TaskDTO() {}

    // ✅ Constructor without taskId (for new insert)
    public TaskDTO(int userId, String title, String description, String dueDate, String priority, String status) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    // ✅ Constructor with taskId (for update/retrieve)
    public TaskDTO(int taskId, int userId, String title, String description, String dueDate, String priority, String status) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    // ✅ Getters & Setters
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
