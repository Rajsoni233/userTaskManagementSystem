package controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class UserHome extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        // Check if session exists
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("adminLogin.html"); // redirect to login if session expired
            return;
        }

        String email = (String) session.getAttribute("email");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>User Task Management System</title>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");

            // Optional: extra JS to prevent back navigation
            out.println("<script type='text/javascript'>");
            out.println("function preventBack() { window.history.forward(); }");
            out.println("setTimeout('preventBack()', 0);");
            out.println("window.onunload = function(){ null };");
            out.println("</script>");

            out.println("<style>");
            out.println("body{font-family:'Segoe UI',Candara,sans-serif;padding:30px;margin:0;background:linear-gradient(to right,#f4f7fb,#e9eff5);color:#333;}");
            out.println("#nav{height:60px;background-color:navy;box-shadow:0 2px 6px rgba(0,0,0,0.2);border-radius:8px;}");
            out.println("#nav ul{display:flex;justify-content:center;align-items:center;height:100%;margin:0;padding:0;list-style:none;}");
            out.println("#nav ul li{margin:0 20px;}");
            out.println("#nav ul li a{        margin-bottom: 10px;"+"font-size:18px;font-weight:500;color:white;text-decoration:none;padding:8px 14px;border-radius:6px;transition:all 0.3s ease;}");
            out.println("#nav ul li a:hover{background-color:#1e90ff;color:#fff;transform:scale(1.05);}");
            out.println("h1{text-align:center;color:navy;margin-top:20px;font-size:32px;text-shadow:1px 1px 3px rgba(0,0,0,0.1);}");
            out.println("p{font-size:18px;line-height:1.6;text-align:justify;color:#444;}");
            out.println("a{text-decoration:none;font-size:16px;color:navy;margin-top:10px;display:inline-block;transition:color 0.3s;}");
            out.println("a:hover{color:#1e90ff;}");
            out.println("@media(max-width:768px){#nav ul{flex-direction:column;}#nav ul li{margin:10px 0;}}");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<h1>User Task Management System</h1>");
            out.println("<div id='nav'><ul>");
            out.println("<li><a href=''>Welcome " + email + "</a></li>");
            out.println("<li><a href='UserHome'>Home</a></li>");
            out.println("<li><a href='UserTasks'>View Tasks</a></li>");
            out.println("<li><a href='UserLogout'>Logout</a></li>");
            out.println("</ul></div>");

            out.println("<h1>User Profile Page</h1>");
            out.println("<p>Welcome, " + email + "!</p>");

            out.println("</body>");
            out.println("</html>");
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
