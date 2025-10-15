package controller;

import dao.UserDAO;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ApproveUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // ✅ Check if admin is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminEmail") == null) {
            out.println("<script>alert('Please login first'); window.location='AdminLogin.html';</script>");
            return; // Stop processing if not logged in
        }

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            UserDAO dao = new UserDAO();
            int i = dao.approveUser(userId);

            if (i > 0) {
                out.println("<script>alert('User Approved Successfully'); window.location='ViewUsersServlet';</script>");
            } else {
                out.println("<script>alert('User Not Approved'); window.location='ViewUsersServlet';</script>");
            }
        } catch (NumberFormatException e) {
            out.println("<script>alert('Invalid User ID'); window.location='ViewUsersServlet';</script>");
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
