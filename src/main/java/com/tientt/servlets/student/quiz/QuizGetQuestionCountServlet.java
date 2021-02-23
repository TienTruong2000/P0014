package com.tientt.servlets.student.quiz;

import com.tientt.blos.interfaces.TblQuestionBLO;
import com.tientt.blos.interfaces.TblTestBLO;
import com.tientt.entities.TblTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuizGetQuestionCountServlet extends HttpServlet {
    private final TblTestBLO testBLO = TblTestBLO.newInstance();
    private final TblQuestionBLO questionBLO = TblQuestionBLO.newInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String testID = request.getParameter("testID");
        TblTest test = testBLO.findTestByID(testID);
        boolean isEnoughQuestion = questionBLO.isEnoughQuestionForTest(test);
        if (!isEnoughQuestion){
            response.setStatus(400);
        } else {
            response.setStatus(200);
        }
    }
}
