package com.tientt.servlets.student.quiz;

import com.tientt.blos.interfaces.TblQuizBLO;
import com.tientt.entities.TblQuiz;
import com.tientt.entities.TblUser;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class QuizTakeServlet extends HttpServlet {


    private final TblQuizBLO quizBLO = TblQuizBLO.newInstance();
    private final String SHOW_QUIZ_PAGE_SERVLET = "ShowQuizAction";
    private final Logger logger = Logger.getLogger(QuizTakeServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testID = request.getParameter("testID");
        HttpSession session = request.getSession(false);
        if (session != null) {
            TblUser user = (TblUser) session.getAttribute("USER");
            String userEmail = user.getEmail();
            try {
                TblQuiz quiz = quizBLO.createQuiz(testID, userEmail);
                String url = SHOW_QUIZ_PAGE_SERVLET + "?quizID="+quiz.getID();
                response.sendRedirect(url);
            } catch (IllegalArgumentException ex) {
                this.log(QuizTakeServlet.class.getName() + ": "+ "IllegalArgumentException: " + ex.getMessage());
                logger.error(ex.getMessage());
                response.sendError(500);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
