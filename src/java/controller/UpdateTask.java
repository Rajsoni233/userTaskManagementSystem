package controller;

import dao.TaskDAO;
import dto.TaskDTO;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        String email = (session != null) ? (String) session.getAttribute("email") : "Guest";
        int userId = (session != null && session.getAttribute("userId") != null) 
                        ? (int) session.getAttribute("userId") : -1;

        int taskId = Integer.parseInt(request.getParameter("taskId"));
        TaskDAO dao = new TaskDAO();
        List<TaskDTO> list = dao.getTasksByUser(userId);

        TaskDTO task = null;
        for (TaskDTO t : list) {
            if (t.getTaskId() == taskId) {
                task = t;
                break;
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print("<!DOCTYPE html><html><head><title>Update Task</title>");
            out.print("<meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.print("<script>(()=>{window.history.forward();})()</script>");
            out.print("<style>");
            out.print("body{font-family:'Segoe UI',Candara,sans-serif;padding:30px;margin:0;"
                    + "background:linear-gradient(to right,#f4f7fb,#e9eff5);color:#333;}");
            out.print("#nav{height:60px;background-color:navy;box-shadow:0 2px 6px rgba(0,0,0,0.2);border-radius:8px;}");
            out.print("#nav ul{display:flex;justify-content:center;align-items:center;height:100%;margin:0;padding:0;list-style:none;}");
            out.print("#nav ul li{margin:0 20px;}");
            out.print("#nav ul li a{margin-bottom: 10px;" +"font-size:18px;font-weight:500;color:white;text-decoration:none;padding:8px 14px;"
                    + "border-radius:6px;transition:all 0.3s ease;}");
            out.print("#nav ul li a:hover{background-color:#1e90ff;color:#fff;transform:scale(1.05);}");
            out.print("h1{text-align:center;color:navy;margin-top:20px;font-size:32px;text-shadow:1px 1px 3px rgba(0,0,0,0.1);}");
            out.print("form{max-width:500px;margin:30px auto;background:#fff;padding:20px;border-radius:10px;"
                    + "box-shadow:0 4px 10px rgba(0,0,0,0.1);}label{font-weight:500;display:block;margin-top:10px;}");
            out.print("input,textarea,select{width:100%;padding:8px;margin-top:5px;border:1px solid #ccc;border-radius:6px;}");
            out.print("input[type=submit]{background:navy;color:white;border:none;cursor:pointer;font-size:16px;margin-top:15px;}");
            out.print("input[type=submit]:hover{background:#1e90ff;}");
            out.print("</style></head><body>");

            // NAVBAR
            out.print("<h1>User Task Management System</h1>");
            out.print("<div id='nav'><ul>");
            out.print("<li><a href=''>Welcome " + email + "</a></li>");
            out.print("<li><a href='UserHome'>Home</a></li>");
            out.print("<li><a href='UserLogout'>Logout</a></li>");
            out.print("</ul></div>");

            if (task != null) {
                out.println("<h1>Update Task</h1>");
                out.println("<form method='post' action='UpdateTask'>");
                out.println("<input type='hidden' name='taskId' value='" + task.getTaskId() + "'/>");

                out.println("<label>Title:</label>");
                out.println("<input type='text' name='title' value='" + task.getTitle() + "' required/>");

                out.println("<label>Description:</label>");
                out.println("<textarea name='desc' required>" + task.getDescription() + "</textarea>");

                out.println("<label>Due Date:</label>");
                out.println("<input type='date' name='due' value='" + task.getDueDate() + "' required/>");

                out.println("<label>Priority:</label>");
                out.println("<select name='priority'>");
                out.println("<option " + (task.getPriority().equals("High") ? "selected" : "") + ">High</option>");
                out.println("<option " + (task.getPriority().equals("Medium") ? "selected" : "") + ">Medium</option>");
                out.println("<option " + (task.getPriority().equals("Low") ? "selected" : "") + ">Low</option>");
                out.println("</select>");

                out.println("<label>Status:</label>");
                out.println("<select name='status'>");
                out.println("<option " + (task.getStatus().equals("Pending") ? "selected" : "") + ">Pending</option>");
                out.println("<option " + (task.getStatus().equals("In Progress") ? "selected" : "") + ">In Progress</option>");
                out.println("<option " + (task.getStatus().equals("Completed") ? "selected" : "") + ">Completed</option>");
                out.println("</select>");

                out.println("<input type='submit' value='Update Task'/>");
                out.println("</form>");
            } else {
                out.print("<script>alert('Task Not Found'); window.location='UserTasks'</script>");
            }

            out.print("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        int taskId = Integer.parseInt(request.getParameter("taskId"));
        int userId = (int) request.getSession().getAttribute("userId");
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        String due = request.getParameter("due");
        String priority = request.getParameter("priority");
        String status = request.getParameter("status");

        TaskDAO dao = new TaskDAO();
        TaskDTO task = new TaskDTO(taskId, userId, title, desc, due, priority, status);
        int rows = dao.updateTask(task);

        if (rows > 0) {
            response.sendRedirect("UserTasks");
        } else {
            response.getWriter().println("<h3>Failed to update task.</h3>");
        }
    }
}
