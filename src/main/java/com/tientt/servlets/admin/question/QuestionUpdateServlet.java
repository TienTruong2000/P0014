package com.tientt.servlets.admin.question;

import com.google.gson.Gson;
import com.tientt.blos.interfaces.TblQuestionBLO;
import com.tientt.requestobjects.QuestionRequestObject;
import com.tientt.validators.QuestionValidator;
import com.tientt.validators.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class QuestionUpdateServlet extends HttpServlet {
    private final TblQuestionBLO questionBLO = TblQuestionBLO.newInstance();
    private final Gson gson = new Gson();

    private QuestionRequestObject getRequestObject(HttpServletRequest request) throws IOException{
        //parse JSON string
        QuestionRequestObject requestObject = null;
        BufferedReader bf = null;
        try {
            bf = request.getReader();
            String jsonString = bf.readLine();
            requestObject = gson.fromJson(jsonString, QuestionRequestObject.class);
        } finally {
            if (bf != null) {
                bf.close();
            }
        }
        return requestObject;

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        QuestionRequestObject requestObject = getRequestObject(request);
        if (requestObject != null){
            Validator<QuestionRequestObject> validator = new QuestionValidator(requestObject);
            validator.validateObject();

            if (validator.hasError()) {
                response.setStatus(400);
                String errorJson = gson.toJson(validator.getErrors());
                PrintWriter out = response.getWriter();
                out.write(errorJson);
            }//end if information is valid
            else {
                questionBLO.updateQuestion(requestObject);
                response.setStatus(200);
            }
        }//end if has request object
        else{
            response.setStatus(400);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
