package controller;

import dao.TaskDAO;
import dto.TaskDTO;
import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class RetriveTask extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("userLogin.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        TaskDAO taskDao = new TaskDAO();
        List<TaskDTO> tasks = taskDao.getTasksByUser(userId);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>My Tasks</title>");
        out.println("<style>");
        out.println("body{font-family:'Segoe UI',Candara,sans-serif;padding:30px;margin:0;background:linear-gradient(to right,#f4f7fb,#e9eff5);}");
        out.println("h1{text-align:center;color:navy;margin-bottom:20px;}");
        out.println("table{border-collapse:collapse;width:90%;margin:auto;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background-color:navy;color:white;}");
        out.println("a.action{color:navy;text-decoration:none;margin:0 5px;}");
        out.println("a.action:hover{color:#1e90ff;}");
        out.println("</style></head><body>");
        out.println("<h1>My Tasks</h1>");
        out.println("<center><a href='AddTaskForm.html'>Add New Task</a> | <a href='UserLogout'>Logout</a></center><br>");

        if (tasks.isEmpty()) {
            out.println("<p style='text-align:center;color:red;'>No tasks found.</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Title</th><th>Description</th><th>Due Date</th><th>Priority</th><th>Actions</th></tr>");
            for (TaskDTO t : tasks) {
                out.println("<tr>");
                out.println("<td>" + t.getTaskId() + "</td>");
                out.println("<td>" + t.getTitle() + "</td>");
                out.println("<td>" + t.getDesc() + "</td>");
                out.println("<td>" + t.getDueDate() + "</td>");
                out.println("<td>" + t.getPriority() + "</td>");
                out.println("<td>"
                        + "<a class='action' href='UpdateTaskForm?taskId=" + t.getTaskId() + "'>Update</a>"
                        + "<a class='action' href='DeleteTask?taskId=" + t.getTaskId() + "' onclick='return confirm(\"Are you sure?\");'>Delete</a>"
                        + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
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
