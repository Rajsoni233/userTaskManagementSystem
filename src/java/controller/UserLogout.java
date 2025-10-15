    package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserLogout extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate session
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        // Set content type
        response.setContentType("text/html;charset=UTF-8");

        // Show alert and redirect
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<script type='text/javascript'>");
            out.println("alert('Logout Successful');");
            out.println("window.location.href='adminLogin.html';"); // redirect to login page
            out.println("</script>");
            out.println("</head>");
            out.println("<body></body>");
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
