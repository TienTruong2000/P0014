package com.tientt.servlets.accesscontroll;

import com.tientt.blos.interfaces.TblUserBLO;
import com.tientt.requestobjects.UserRequestObject;
import com.tientt.utils.SiteMapHelper;
import com.tientt.validators.UserValidator;
import com.tientt.validators.Validator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;


public class RegisterServlet extends HttpServlet {
    private static final String REGISTER_PAGE = "RegisterPage";
    private static final String LOGIN_PAGE = "loginPage";
    private final Logger logger = Logger.getLogger(RegisterServlet.class);
    private final TblUserBLO userBLO = TblUserBLO.newInstance();

    private UserRequestObject getRequestObject(HttpServletRequest request) {
        String email = request.getParameter("txtEmail").trim();
        String password = request.getParameter("txtPassword").trim();
        String confirmedPassword = request.getParameter("txtConfirmedPassword").trim();
        String fullname = request.getParameter("txtFullname").trim();
        UserRequestObject requestObject = new UserRequestObject(email, password,
                confirmedPassword, fullname);
        return requestObject;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Document siteMap = (Document) this.getServletContext().getAttribute("DOMTREE");
            String url;
            //get and validate user request
            UserRequestObject requestObject = getRequestObject(request);
            Validator<UserRequestObject> validator = new UserValidator(requestObject);
            validator.validateObject();

            if (validator.hasError()) {
                request.setAttribute("ERROR", validator.getErrors());
                url = SiteMapHelper.getURL(REGISTER_PAGE, siteMap);
                request.getRequestDispatcher(url).forward(request, response);
            }//end if information is valid
            else {
                url = LOGIN_PAGE + "?register=true";
                userBLO.insertAccount(requestObject);
                response.sendRedirect(url);
            }
        } catch (XPathExpressionException ex) {
            this.log(RegisterServlet.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
            logger.error(ex.getMessage());
            response.sendError(500);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
