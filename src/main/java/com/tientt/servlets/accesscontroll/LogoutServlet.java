package com.tientt.servlets.accesscontroll;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LogoutServlet extends HttpServlet {
    private final String LOGIN_PAGE = "loginPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String url = LOGIN_PAGE + "?logout=true";
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        response.sendRedirect(url);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
