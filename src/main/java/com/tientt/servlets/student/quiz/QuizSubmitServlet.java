package com.tientt.servlets.student.quiz;

import com.google.gson.Gson;
import com.tientt.blos.interfaces.TblQuizBLO;
import com.tientt.blos.interfaces.TblQuizQuestionBLO;
import com.tientt.commons.Constant;
import com.tientt.entities.TblQuiz;
import com.tientt.entities.TblUser;
import com.tientt.requestobjects.SubmitRequestObject;
import com.tientt.utils.SiteMapHelper;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathExpressionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class QuizSubmitServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final TblQuizBLO quizBLO = TblQuizBLO.newInstance();


    private List<SubmitRequestObject> getListSubmitRequestObject(HttpServletRequest request) throws IOException {
        BufferedReader bf = null;
        try {
            bf = request.getReader();
            String json = bf.readLine();
            SubmitRequestObject[] requestObjects = gson.fromJson(json, SubmitRequestObject[].class);
            return Arrays.asList(requestObjects);
        } finally {
            if (bf != null) {
                bf.close();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizID = request.getParameter("quizID");
        List<SubmitRequestObject> submitRequestObjectList = getListSubmitRequestObject(request);
        TblQuiz quiz = quizBLO.findQuizByID(quizID);

        HttpSession session = request.getSession(false);
        TblUser user = (TblUser) session.getAttribute("USER");
        String userEmail = user.getEmail();
        String quizUserEmail = quiz.getUser().getEmail();
        if (!userEmail.equals(quizUserEmail)) {
            response.setContentType("text/html");
            response.sendError(403);
        } // end if this quiz belong to correct user
         else {
            response.setContentType("application/json");
            response.setStatus(200);
            long submitTime = new Date().getTime();
            if (submitTime <= quiz.getBeginTime() + quiz.getTest().getTestTimeLength() * 60 * 1000 + Constant.DELAY_TIME * 1000){
                quizBLO.submitQuiz(quizID, submitRequestObjectList, submitTime);
            } else{
                quizBLO.submitQuiz(quizID, quiz.getEndTime());
            }
        }
    }
}
