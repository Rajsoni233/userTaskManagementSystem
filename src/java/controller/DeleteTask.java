package controller;

import dao.TaskDAO;
import java.io.*;
import static java.lang.System.out;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteTask extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        int taskId = Integer.parseInt(request.getParameter("taskId"));
        TaskDAO dao = new TaskDAO();
        int rows = dao.deleteTask(taskId);

        if (rows > 0) {
           RequestDispatcher rd = request.getRequestDispatcher("UserTasks");
           out.print("<script>alert('Invalid Login')</script>");
           rd.forward(request, response);
        } else {

RequestDispatcher rd = request.getRequestDispatcher("UserTasks");
out.println("alert('Failed to delete task.');");
rd.include(request, response);        }
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
