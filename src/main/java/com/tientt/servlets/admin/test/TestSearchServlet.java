package com.tientt.servlets.admin.test;

import com.tientt.blos.interfaces.TblSubjectBLO;
import com.tientt.blos.interfaces.TblTestBLO;
import com.tientt.entities.TblSubject;
import com.tientt.entities.TblTest;
import com.tientt.utils.SiteMapHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

public class TestSearchServlet extends HttpServlet {
    private final TblTestBLO testBLO = TblTestBLO.newInstance();
    private final TblSubjectBLO subjectBLO = TblSubjectBLO.newInstance();
    private final String SHOW_TEST_ADMIN_PAGE = "TestAdminPage";
    private final Logger logger = Logger.getLogger(TestSearchServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subjectID = request.getParameter("sSubjectID");

        try {
            List<TblTest> testList;
            if (subjectID == null || subjectID.trim().isEmpty()){
                testList = testBLO.findAllTestOrderByCreateDate();
            } else{
                testList = testBLO.findAllTestBySubjectOrderByCreateDate(subjectID);
            }

            request.setAttribute("TEST_LIST", testList);

            List<TblSubject> subjectList = subjectBLO.findAllSubject();
            request.setAttribute("SUBJECT_LIST", subjectList);

            Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
            String url = SiteMapHelper.getURL(SHOW_TEST_ADMIN_PAGE, document);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (XPathExpressionException ex) {
            this.log(TestSearchServlet.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
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
