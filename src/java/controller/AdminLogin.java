package controller;

import dao.AdminDAO;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent caching (back button issue)
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            AdminDAO dao = new AdminDAO();
            int i = dao.loginAdmin(email, password);

            if (i > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("adminEmail", email);

                out.println("<script>alert('Login Successful'); window.location='AdminHome';</script>");

            } else {
                out.println("<script>alert('Error While Login'); window.location='AdminLogin.html';</script>");
            }
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
