package com.tientt.servlets.admin.question;

import com.tientt.blos.interfaces.TblQuestionBLO;
import com.tientt.commons.Constant;
import com.tientt.requestobjects.ChoiceRequestObject;
import com.tientt.requestobjects.QuestionRequestObject;
import com.tientt.utils.SiteMapHelper;
import com.tientt.validators.QuestionValidator;
import com.tientt.validators.Validator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionCreateServlet extends HttpServlet {

    private final String SHOW_ADMIN_PAGE_SERVLET = "ShowAdminPageAction";
    private final String SHOW_CREATE_QUESTION_SERVLET = "ShowCreateQuestionPageAction";
    private final TblQuestionBLO questionBLO = TblQuestionBLO.newInstance();
    private final Logger logger = Logger.getLogger(QuestionCreateServlet.class);

    private QuestionRequestObject getRequestObject(HttpServletRequest request) {
        String subjectID = request.getParameter("cSubjectID");
        String content = request.getParameter("cQuestionContent");
        String correctChoice = request.getParameter("correct");


        List<ChoiceRequestObject> choiceList = new ArrayList<>();
        for (int i = 0; i < Constant.QUESTION_MAX_CHOICE; i++){
            String choiceContent = request.getParameter("choiceContent"+i);
            ChoiceRequestObject choice = new ChoiceRequestObject();
            choice.setChoiceContent(choiceContent.trim());
            choiceList.add(choice);

        }

        QuestionRequestObject question = new QuestionRequestObject();
        question.setContent(content.trim());
        question.setSubjectID(subjectID.trim());
        question.setCorrectChoiceID(correctChoice);
        question.setChoices(choiceList);

        return question;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        try{
            QuestionRequestObject questionRequestObject = getRequestObject(request);
            Validator<QuestionRequestObject> validator = new QuestionValidator(questionRequestObject);
            validator.validateObject();

            if (validator.hasError()) {
                request.setAttribute("ERROR", validator.getErrors());
                Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
                String url = SiteMapHelper.getURL(SHOW_CREATE_QUESTION_SERVLET, document);
                request.getRequestDispatcher(url).forward(request, response);
            }//end if information is valid
            else {
                questionBLO.insertQuestion(questionRequestObject);
                String url = SHOW_ADMIN_PAGE_SERVLET;
                response.sendRedirect(url);
            }
        } catch (XPathExpressionException ex) {
            this.log(QuestionCreateServlet.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
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
