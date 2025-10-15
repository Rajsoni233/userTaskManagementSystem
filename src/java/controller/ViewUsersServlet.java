package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ViewUsersServlet extends HttpServlet {

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
        if (session == null || session.getAttribute("adminEmail") == null) {
            response.sendRedirect("adminLogin.html");
            return;
        }

        String adminEmail = (String) session.getAttribute("adminEmail");

        UserDAO dao = new UserDAO();
        List<UserDTO> users = dao.getAllUsersList();

        // HTML Header and Nav
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head><title>Admin Panel</title>");
        out.println("<meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1.0'>");
        out.println("<style>"
                + "body{font-family:'Segoe UI',Candara,sans-serif;padding:30px;margin:0;background:linear-gradient(to right,#f4f7fb,#e9eff5);color:#333;}"
                + "#nav{height:60px;background-color:navy;box-shadow:0 2px 6px rgba(0,0,0,0.2);border-radius:8px;}"
                + "#nav ul{display:flex;justify-content:center;align-items:center;height:100%;margin:0;padding:0;list-style:none;}"
                + "#nav ul li{margin:0 20px;}"
                + "#nav ul li a{font-size:18px;font-weight:500;color:white;text-decoration:none;padding:8px 14px;border-radius:6px;transition:all 0.3s ease;}"
                + "#nav ul li a:        margin-bottom: 10px;"+"hover{background-color:#1e90ff;color:#fff;transform:scale(1.05);}"
                + "h1{text-align:center;color:navy;margin-top:20px;font-size:32px;text-shadow:1px 1px 3px rgba(0,0,0,0.1);}"
                + "table{margin:20px auto;border-collapse:collapse;width:95%;}"
                + "table,th,td{border:1px solid #ccc;}"
                + "th,td{padding:10px;text-align:center;font-size:16px;}"
                + "th{background:navy;color:white;}"
                + "a.action{color:navy;font-weight:500;margin:0 5px;}"
                + "a.action:hover{color:#1e90ff;}"
                + "button{background:navy;color:white;border:none;padding:6px 12px;font-size:14px;border-radius:6px;cursor:pointer;}"
                + "button:hover{background:#1e90ff;}"
                + "</style></head><body>");

        out.println("<h1>User Task Management System</h1>");
        out.println("<div id='nav'><ul>"
                + "<li><a href='#'>Welcome " + adminEmail + "</a></li>"
                + "<li><a href='AdminHome'>Home</a></li>"
                + "<li><a href='ViewUsersServlet'>View All Users</a></li>"
                + "<li><a href='ViewUserTask'>View User Task</a></li>"
                + "<li><a href='AdminLogout'>Logout</a></li>"
                + "</ul></div>");

        // Users table
        out.println("<h1>All Users</h1>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Username</th><th>Email</th><th>Address</th>"
                + "<th>Approved</th><th>Blocked</th><th>Actions</th></tr>");

        for (UserDTO u : users) {
            out.println("<tr>");
            out.println("<td>" + u.getUserId() + "</td>");
            out.println("<td>" + u.getUsername() + "</td>");
            out.println("<td>" + u.getEmail() + "</td>");
            out.println("<td>" + u.getAddress() + "</td>");
            out.println("<td>" + (u.getApproved() == 1 ? "Yes" : "No") + "</td>");
            out.println("<td>" + (u.getBlocked() == 1 ? "Yes" : "No") + "</td>");
            out.println("<td>");

            if (u.getApproved() == 0) {
                out.println("<a class='action' href='ApproveUser?userId=" + u.getUserId() + "'><button>Approve</button></a>");
            } else {
                out.println("<span style='color:green;font-weight:bold;'>Approved</span>");
            }

            if (u.getBlocked() == 0) {
                out.println("<a class='action' href='BlockUserServlet?userId=" + u.getUserId() + "'><button>Block</button></a>");
            } else {
                out.println("<span style='color:red;font-weight:bold;'>Blocked</span>");
            }

            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</table>");
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
