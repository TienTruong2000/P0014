package com.tientt.servlets.student.quiz;

import com.tientt.blos.interfaces.TblQuizBLO;
import com.tientt.blos.interfaces.TblSubjectBLO;
import com.tientt.entities.TblQuiz;
import com.tientt.entities.TblSubject;
import com.tientt.entities.TblUser;
import com.tientt.utils.SiteMapHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

public class QuizShowHistoryServlet extends HttpServlet {
    private final TblQuizBLO quizBLO = TblQuizBLO.newInstance();
    private final TblSubjectBLO subjectBLO = TblSubjectBLO.newInstance();
    private final String QUIZ_HISTORY_PAGE = "QuizHistoryPage";
    private final Logger logger = Logger.getLogger(QuizShowHistoryServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        try {
            String subjectID = request.getParameter("subjectID");
            List<TblQuiz>  quizList;
            HttpSession session = request.getSession(false);
            TblUser user = (TblUser) session.getAttribute("USER");
            if (subjectID == null || subjectID.trim().isEmpty()){
                quizList = quizBLO.findAllQuizByUserEmail(user.getEmail());
            } else {
                quizList = quizBLO.findAllQuizByUserEmailAndSubjectID(user.getEmail(), subjectID);
            }

            List<TblSubject> subjectList = subjectBLO.findAllSubject();
            request.setAttribute("QUIZ_LIST", quizList);
            request.setAttribute("SUBJECT_LIST", subjectList);

            Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
            String url = SiteMapHelper.getURL(QUIZ_HISTORY_PAGE, document);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (XPathExpressionException ex) {
            this.log(QuizShowHistoryServlet.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
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
