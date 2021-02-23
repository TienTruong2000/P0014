package com.tientt.servlets.accesscontroll;

import com.tientt.blos.interfaces.TblUserBLO;
import com.tientt.commons.Role;
import com.tientt.entities.TblUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginServlet extends HttpServlet {
    private final TblUserBLO userBLO = TblUserBLO.newInstance();
    private final static String LOGIN_PAGE = "loginPage";
    private final static String SHOW_ADMIN_PAGE_SERVLET = "ShowAdminPageAction";
    private final static String SHOW_STUDENT_PAGE_SERVLET = "ShowStudentPageAction";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String url = LOGIN_PAGE + "?error=true";
        TblUser user = userBLO.findUserByEmailAndPassword(email, password);
        if (user != null) {
            HttpSession session = request.getSession(false);
            session.setAttribute("USER", user);
            if (user.getRole().getID() == Role.ADMIN) {
                url = SHOW_ADMIN_PAGE_SERVLET;
            } else if (user.getRole().getID() == Role.STUDENT) {
                url = SHOW_STUDENT_PAGE_SERVLET;
            }
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
