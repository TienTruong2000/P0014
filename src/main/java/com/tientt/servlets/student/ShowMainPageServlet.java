package com.tientt.servlets.student;

import com.tientt.blos.interfaces.TblQuizBLO;
import com.tientt.blos.interfaces.TblSubjectBLO;
import com.tientt.blos.interfaces.TblTestBLO;
import com.tientt.commons.TestStatus;
import com.tientt.entities.TblQuiz;
import com.tientt.entities.TblSubject;
import com.tientt.entities.TblTest;
import com.tientt.entities.TblUser;
import com.tientt.models.TestModel;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowMainPageServlet extends HttpServlet {
    private final TblTestBLO testBLO = TblTestBLO.newInstance();
    private final TblSubjectBLO subjectBLO = TblSubjectBLO.newInstance();
    private final TblQuizBLO quizBLO = TblQuizBLO.newInstance();
    private final String STUDENT_PAGE = "StudentPage";
    private final Logger logger = Logger.getLogger(ShowMainPageServlet.class);

    private TestStatus calculateTestStatus(TblTest test, String userEmail) {
        TestStatus testStatus = null;
        TblQuiz quiz = quizBLO.findQuizByTestIDAndUserEmail(test.getID(), userEmail);

        if (quiz != null) {
            if (quiz.isSubmitted()) {
                testStatus = TestStatus.IS_SUBMITTED;
            } else {
                testStatus = TestStatus.ON_GOING;
            }
        } else {
            Long currentTime = new Date().getTime();
            if (currentTime < test.getOpenTime()) {
                testStatus = TestStatus.NOT_OPEN;
            }
            if (currentTime >= test.getOpenTime() && currentTime <= test.getDeadlineTime()) {
                testStatus = TestStatus.OPENED;
            }
            if (currentTime > test.getDeadlineTime()) {
                testStatus = TestStatus.CLOSED;
            }
        }
        return testStatus;
    }

    private List<TestModel> mappingToTestModel(List<TblTest> tblTestList, String userEmail) {
        List<TestModel> testModelList = new ArrayList<>();
        for (TblTest tblTest : tblTestList) {
            TestModel model = new TestModel();
            model.setName(tblTest.getName());
            model.setDeadline(tblTest.getDeadlineTime());
            model.setLength(tblTest.getTestTimeLength());
            model.setNumOfQuestion(tblTest.getNumOfQuestion());
            model.setOpenTime(tblTest.getOpenTime());
            model.setSubject(tblTest.getSubject());
            model.setStatus(this.calculateTestStatus(tblTest, userEmail));
            model.setID(tblTest.getID());

            TblQuiz quiz = quizBLO.findQuizByTestIDAndUserEmail(tblTest.getID(),userEmail);
            if (quiz != null) {
                model.setQuiz(quiz);
            }
            testModelList.add(model);
        }
        return testModelList;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subjectID = request.getParameter("sSubjectID");
        HttpSession session = request.getSession(false);
        TblUser user = (TblUser) session.getAttribute("USER");
        try {
            List<TblTest> testList;
            if (subjectID == null || subjectID.trim().isEmpty()) {
                testList = testBLO.findAllTestOrderByCreateDate();
            } else {
                testList = testBLO.findAllTestBySubjectOrderByCreateDate(subjectID);
            }

            List<TestModel> modelList = mappingToTestModel(testList, user.getEmail());
            request.setAttribute("TEST_LIST", modelList);

            List<TblSubject> subjectList = subjectBLO.findAllSubject();
            request.setAttribute("SUBJECT_LIST", subjectList);

            Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
            String url = SiteMapHelper.getURL(STUDENT_PAGE, document);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (XPathExpressionException ex) {
            this.log(ShowMainPageServlet.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
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
