package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserDAO dao = new UserDAO();
            UserDTO user = dao.checkUserLogin(email, password);

            if (user != null) {
                if (user.getApproved() == 1 && user.getBlocked() == 0) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId", user.getUserId());
                    session.setAttribute("email", email);

                    // Login success alert + redirect
                    out.println("<script>alert('Login Successful'); window.location='UserHome';</script>");
                } else {
                    out.println("<script>alert('Account Not Approved or Blocked by Admin'); window.location='userLogin.html';</script>");
                }
            } else {
                out.println("<script>alert('Invalid Login'); window.location='userLogin.html';</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("userLogin.html");
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
