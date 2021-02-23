package com.tientt.servlets.student.quiz;

import com.tientt.blos.interfaces.TblQuizBLO;
import com.tientt.entities.TblQuiz;
import com.tientt.utils.SiteMapHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class QuizShowResultPageServlet extends HttpServlet {
    private final TblQuizBLO quizBLO = TblQuizBLO.newInstance();
    private final String RESULT_PAGE = "ResultPage";
    private final Logger logger = Logger.getLogger(QuizShowResultPageServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String quizID = request.getParameter("quizID");
            TblQuiz quiz = quizBLO.findQuizByID(quizID);


            request.setAttribute("QUIZ", quiz);

            Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
            String url = SiteMapHelper.getURL(RESULT_PAGE, document);;
            request.getRequestDispatcher(url).forward(request, response);
        } catch (XPathExpressionException ex) {
            this.log(QuizShowResultPageServlet.class.getName() + ": " + "XPathExpressionException: " + ex.getMessage());
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
