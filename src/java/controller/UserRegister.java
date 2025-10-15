package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.regex.*;

public class UserRegister extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String name = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String address = request.getParameter("address");

            // ✅ Password validation: at least 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special symbol
            String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
            Pattern pattern = Pattern.compile(passwordPattern);
            Matcher matcher = pattern.matcher(password);

            if (!matcher.matches()) {
                out.println("<script>"
                        + "alert('Password must be at least 8 characters and include uppercase, lowercase, number, and special character.');"
                        + "window.location='userRegister.html';"
                        + "</script>");
                return; // stop further execution
            }

            // ✅ If password is valid, proceed with registration
            UserDTO user = new UserDTO();
            user.setUsername(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setAddress(address);

            UserDAO dao = new UserDAO();
            int rows = dao.registerUser(user);

            if (rows > 0) {
                out.println("<script>alert('Registration Successful'); window.location='userLogin.html';</script>");
            } else {
                out.println("<script>alert('Error during registration'); window.location='userRegister.html';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("userRegister.html");
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
