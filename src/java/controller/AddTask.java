package controller;

import dao.TaskDAO;
import dto.TaskDTO;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddTask extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            out.println("<script>alert('Please login first'); window.location='userLogin.html';</script>");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        String due = request.getParameter("due");
        String priority = request.getParameter("priority");

        TaskDTO task = new TaskDTO();
        task.setUserId(userId);
        task.setTitle(title);
        task.setDescription(desc);
        task.setDueDate(due);
        task.setPriority(priority);
        task.setStatus("Pending");

        TaskDAO dao = new TaskDAO();
        int rows = dao.addTask(task);

        if (rows > 0) {
            out.println("<script>alert('Task Added Successfully'); window.location='UserTasks';</script>");
        } else {
            out.println("<script>alert('Error while Adding Task'); window.location='UserTasks';</script>");
        }
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
