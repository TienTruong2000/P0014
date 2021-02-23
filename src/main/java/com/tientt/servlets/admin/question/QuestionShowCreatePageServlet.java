package com.tientt.servlets.admin.question;

import com.tientt.blos.interfaces.TblSubjectBLO;
import com.tientt.entities.TblSubject;
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

public class QuestionShowCreatePageServlet extends HttpServlet {
    private final String CREATE_QUESTION_PAGE = "CreateQuestionPage";
    private TblSubjectBLO subjectBLO = TblSubjectBLO.newInstance();
    private final Logger logger = Logger.getLogger(QuestionShowCreatePageServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        try {
            List<TblSubject> subjectList = subjectBLO.findAllSubject();
            request.setAttribute("SUBJECT_LIST", subjectList);

            Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
            String url = SiteMapHelper.getURL(CREATE_QUESTION_PAGE, document);

            request.getRequestDispatcher(url).forward(request, response);
        } catch (XPathExpressionException ex) {
            this.log(QuestionShowCreatePageServlet.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
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
