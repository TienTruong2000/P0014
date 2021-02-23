package com.tientt.servlets.admin.test;

import com.tientt.blos.interfaces.TblTestBLO;
import com.tientt.requestobjects.TestRequestObject;
import com.tientt.utils.SiteMapHelper;
import com.tientt.validators.TestValidator;
import com.tientt.validators.Validator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.text.ParseException;

public class TestCreateServlet extends HttpServlet {
    private final String SHOW_TEST_ADMIN_SERVLET = "ShowTestAdminAction";
    private final String SHOW_CREATE_TEST_SERVLET = "ShowCreateTestPageAction";
    private final TblTestBLO testBLO = TblTestBLO.newInstance();
    private final Logger logger = Logger.getLogger(TestCreateServlet.class);

    private TestRequestObject getRequestObject(HttpServletRequest request) {
        String name = request.getParameter("cTestName");
        String openTime = request.getParameter("cTestOpenTime");
        String deadlineTime = request.getParameter("cTestDeadlineTime");
        String timeLength = request.getParameter("cTestTimeLength");
        String numOfQuestion = request.getParameter("cTestNumOfQuestion");
        String subjectID = request.getParameter("cTestSubjectID");

        TestRequestObject requestObject = new TestRequestObject(name.trim(), openTime.trim(),
                deadlineTime.trim(), timeLength.trim(), numOfQuestion.trim(), subjectID.trim());
        return requestObject;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TestRequestObject requestObject = getRequestObject(request);

        Validator<TestRequestObject> validator = new TestValidator(requestObject);
        validator.validateObject();
        try {
            if (validator.hasError()) {
                request.setAttribute("ERROR", validator.getErrors());
                Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
                String url = SiteMapHelper.getURL(SHOW_CREATE_TEST_SERVLET, document);
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                testBLO.insertTest(requestObject);
                String url = SHOW_TEST_ADMIN_SERVLET;
                response.sendRedirect(url);
            }
        } catch (ParseException ex) {
            this.log(TestCreateServlet.class.getName() + ": "+ "ParseException: " + ex.getMessage());
            logger.error(ex.getMessage());
            response.sendError(500);
        } catch (XPathExpressionException ex) {
            this.log(TestCreateServlet.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
            logger.error(ex.getMessage());
            response.sendError(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
