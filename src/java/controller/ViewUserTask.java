package controller;

import dao.UserDAO;
import dao.TaskDAO;
import dto.UserDTO;
import dto.TaskDTO;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewUserTask extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        // Session validation
        if (session == null || session.getAttribute("adminEmail") == null) {
            response.sendRedirect("adminLogin.html");
            return;
        }

        String adminEmail = (String) session.getAttribute("adminEmail");

        // Get all users
        UserDAO userDao = new UserDAO();
        List<UserDTO> users = userDao.getAllUsersList();

        // HTML Header and Navigation
        out.print("<!DOCTYPE html><html lang='en'><head><title>Users and Tasks</title>");
        out.print("<meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1.0'>");
        out.print("<style>");
        out.print("body{font-family:'Segoe UI',Candara,sans-serif;padding:30px;margin:0;background:linear-gradient(to right,#f4f7fb,#e9eff5);color:#333;}");
        out.print("#nav{height:60px;background-color:navy;box-shadow:0 2px 6px rgba(0,0,0,0.2);border-radius:8px;}");
        out.print("#nav ul{display:flex;justify-content:center;align-items:center;height:100%;margin:0;padding:0;list-style:none;}");
        out.print("#nav ul li{margin:0 20px;} #nav ul li a{font-size:18px;font-weight:500;color:white;text-decoration:none;padding:8px 14px;border-radius:6px;transition:all 0.3s ease;}");
        out.print("#nav ul li a:        margin-bottom: 10px;"+"hover{background-color:#1e90ff;color:#fff;transform:scale(1.05);} h1{text-align:center;color:navy;margin-top:20px;font-size:32px;text-shadow:1px 1px 3px rgba(0,0,0,0.1);}");
        out.print("table{border-collapse:collapse;margin:20px auto;} table, th, td{border:1px solid #ccc;} th, td{padding:8px;text-align:center;} a{text-decoration:none;color:navy;} a:hover{color:#1e90ff;}");
        out.print("</style></head><body>");
        out.print("<h1>User Task Management System</h1>");
        out.print("<div id='nav'><ul>");
        out.print("<li><a href='#'>Welcome " + adminEmail + "</a></li>");
        out.print("<li><a href='AdminHome'>Home</a></li>");
        out.print("<li><a href='ViewUsersServlet'>View All Users</a></li>");
        out.print("<li><a href='ViewUserTask'>View User Task</a></li>");
        out.print("<li><a href='AdminLogout'>Logout</a></li>");
        out.print("</ul></div>");

        // Display users
        if (users != null && !users.isEmpty()) {
            out.println("<h2 align='center'>All Users</h2>");
            out.println("<table border='1' cellspacing='0' cellpadding='8' align='center'>");
            out.println("<tr><th>User ID</th><th>Name</th><th>Email</th><th>Address</th><th>View Tasks</th></tr>");

            for (UserDTO u : users) {
                out.println("<tr>");
                out.println("<td>" + u.getUserId() + "</td>");
                out.println("<td><a href='ViewUserTask?userId=" + u.getUserId() + "'>" + u.getUsername() + "</a></td>");
                out.println("<td>" + u.getEmail() + "</td>");
                out.println("<td>" + u.getAddress() + "</td>");
                out.println("<td><a href='ViewUserTask?userId=" + u.getUserId() + "'>View</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");
        } else {
            out.println("<h2 align='center'>No Users Found</h2>");
        }

        // Display tasks if a userId is selected
        String paramUserId = request.getParameter("userId");
        if (paramUserId != null) {
            int userId = Integer.parseInt(paramUserId);
            TaskDAO taskDao = new TaskDAO();
            List<TaskDTO> tasks = taskDao.getTasksByUser(userId);

            out.println("<h2 align='center'>Tasks for User ID: " + userId + "</h2>");
            if (tasks.isEmpty()) {
                out.println("<p align='center'>No tasks found for this user.</p>");
            } else {
                out.println("<table border='1' cellspacing='0' cellpadding='8' align='center'>");
                out.println("<tr><th>Title</th><th>Description</th><th>Due</th><th>Priority</th><th>Status</th></tr>");

                for (TaskDTO t : tasks) {
                    out.println("<tr>");
                    out.println("<td>" + t.getTitle() + "</td>");
                    out.println("<td>" + t.getDescription() + "</td>");
                    out.println("<td>" + t.getDueDate() + "</td>");
                    out.println("<td>" + t.getPriority() + "</td>");
                    out.println("<td>" + t.getStatus() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
        }

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
