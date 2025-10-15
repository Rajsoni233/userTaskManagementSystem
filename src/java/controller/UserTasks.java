package controller;

import dao.TaskDAO;
import dto.TaskDTO;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserTasks extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Session validation
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("userLogin.html");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String email = (String) session.getAttribute("email");

        TaskDAO dao = new TaskDAO();
        List<TaskDTO> tasks = dao.getTasksByUser(userId);

        // HTML header
        out.print("<!DOCTYPE html><html lang='en'><head><title>User Tasks</title>");
        out.print("<meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1.0'>");
        out.print("<style>");
        out.print("body{font-family:'Segoe UI',Candara,sans-serif;padding:30px;margin:0;background:linear-gradient(to right,#f4f7fb,#e9eff5);color:#333;}");
        out.print("#nav{height:60px;background-color:navy;box-shadow:0 2px 6px rgba(0,0,0,0.2);border-radius:8px;}");
        out.print("#nav ul{display:flex;justify-content:center;align-items:center;height:100%;margin:0;padding:0;list-style:none;}");
        out.print("#nav ul li{margin:0 20px;} #nav ul li a{font-size:18px;font-weight:500;color:white;text-decoration:none;padding:8px 14px;border-radius:6px;transition:all 0.3s ease;}");
        out.print("#nav ul li a:        margin-bottom: 10px;"+"hover{background-color:#1e90ff;color:#fff;transform:scale(1.05);}");
        out.print("h1{text-align:center;color:navy;margin-top:20px;font-size:32px;text-shadow:1px 1px 3px rgba(0,0,0,0.1);}");
        out.print("table{margin:20px auto;border-collapse:collapse;width:95%;}");
        out.print("table,th,td{border:1px solid #ccc;} th,td{padding:10px;text-align:center;font-size:16px;} th{background:navy;color:white;}");
        out.print("a{color:navy;text-decoration:none;} a:hover{color:#1e90ff;}");
        out.print("button{background:navy;color:white;border:none;padding:6px 12px;font-size:14px;border-radius:6px;cursor:pointer;} button:hover{background:#1e90ff;}");
        out.print("</style></head><body>");

        out.println("<h1>User Task Management System</h1>");
        out.println("<div id='nav'><ul>");
         out.println("<li><a href=''>Welcome " + email + "</a></li>");
            out.println("<li><a href='UserHome'>Home</a></li>");
            out.println("<li><a href='UserTasks'>View Tasks</a></li>");
            out.println("<li><a href='UserLogout'>Logout</a></li>");
        out.println("</ul></div>");

        out.println("<h2 align='center'>My Tasks</h2>");
        if (tasks == null || tasks.isEmpty()) {
            out.println("<p align='center'>You have no tasks.</p>");
        } else {
            out.println("<table border='1' cellspacing='0' cellpadding='8' align='center'>");
            out.println("<tr><th>Title</th><th>Description</th><th>Due</th><th>Priority</th><th>Status</th><th>Actions</th></tr>");
            for (TaskDTO t : tasks) {
                out.println("<tr>");
                out.println("<td>" + t.getTitle() + "</td>");
                out.println("<td>" + t.getDescription() + "</td>");
                out.println("<td>" + t.getDueDate() + "</td>");
                out.println("<td>" + t.getPriority() + "</td>");
                out.println("<td>" + t.getStatus() + "</td>");
                out.println("<td><a href='UpdateTask?taskId=" + t.getTaskId() + "'>Update</a> | " +
                            "<a href='DeleteTask?taskId=" + t.getTaskId() + "'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("<br><a href='addTask.html'>Add New Task</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
