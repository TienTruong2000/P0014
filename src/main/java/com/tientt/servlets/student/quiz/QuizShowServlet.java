package com.tientt.servlets.student.quiz;

import com.tientt.blos.interfaces.TblQuizBLO;
import com.tientt.commons.Constant;
import com.tientt.entities.TblQuiz;
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
import java.util.Date;


public class QuizShowServlet extends HttpServlet {
    private final TblQuizBLO quizBLO = TblQuizBLO.newInstance();
    private final String QUIZ_PAGE = "QuizPage";
    private final String SHOW_QUIZ_RESULT_SERVLET = "ShowResultAction";
    private final Logger logger = Logger.getLogger(QuizShowServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
            String quizID = request.getParameter("quizID");
            TblQuiz quiz = quizBLO.findQuizByID(quizID);

            HttpSession session = request.getSession(false);
            TblUser user = (TblUser) session.getAttribute("USER");
            String userEmail = user.getEmail();
            String quizUserEmail = quiz.getUser().getEmail();
            if (!userEmail.equals(quizUserEmail)) {
                response.sendError(401);
            } else if (quiz.isSubmitted()){
                response.sendRedirect(SHOW_QUIZ_RESULT_SERVLET+"?quizID=" + quizID);
            }
            else{
                long currentTime = new Date().getTime();
                if (currentTime > quiz.getBeginTime() + quiz.getTest().getTestTimeLength() * 60 * 1000 + Constant.DELAY_TIME * 1000){
                    quizBLO.submitQuiz(quizID, currentTime);
                    response.sendRedirect(SHOW_QUIZ_RESULT_SERVLET+"?quizID=" + quizID);
                }//end if user take/continue quiz after quiz length
                else{
                    String url = SiteMapHelper  .getURL(QUIZ_PAGE, document);
                    request.setAttribute("QUIZ", quiz);
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
        } catch (XPathExpressionException ex) {
            this.log(QuizShowServlet.class.getName() + ": " + "XPathExpressionException: " + ex.getMessage());
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
