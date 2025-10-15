package dao;

import dto.TaskDTO;
import java.sql.*;
import java.util.*;
import dbConfig.GetConnection;

public class TaskDAO {

    // Add Task
    public int addTask(TaskDTO task) {
        int rows = 0;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "INSERT INTO tasks(userId, title, description, duedate, priority, status) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, task.getUserId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getDescription());
            ps.setString(4, task.getDueDate());
            ps.setString(5, task.getPriority());
            ps.setString(6, task.getStatus());
            rows = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return rows;
    }

    // Get All Tasks by User
    public List<TaskDTO> getTasksByUser(int userId) {
        List<TaskDTO> list = new ArrayList<>();
        try (Connection con = GetConnection.getConnect()) {
            String sql = "SELECT * FROM tasks WHERE userId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
           while (rs.next()) {
        TaskDTO task = new TaskDTO(
        rs.getInt("taskId"),
        rs.getInt("userId"),
        rs.getString("title"),
        rs.getString("description"),
        rs.getString("duedate"),
        rs.getString("priority"),
        rs.getString("status")
    );
    
    list.add(task);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Delete Task
    public int deleteTask(int taskId) {
        int rows = 0;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "DELETE FROM tasks WHERE taskId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, taskId);
            rows = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return rows;
    }

    // Update Task
    public int updateTask(TaskDTO task) {
        int i = 0;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "UPDATE tasks SET title=?, description=?, duedate=?, priority=?, status=? WHERE taskId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getDueDate());
            ps.setString(4, task.getPriority());
            ps.setString(5, task.getStatus());
            ps.setInt(6, task.getTaskId());
            i = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return i;
    }
}
