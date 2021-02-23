package com.tientt.blos.implementations;

import com.tientt.blos.interfaces.TblQuizBLO;
import com.tientt.blos.interfaces.TblQuizQuestionBLO;
import com.tientt.commons.Constant;
import com.tientt.daos.interfaces.TblQuizDAO;
import com.tientt.daos.interfaces.TblTestDAO;
import com.tientt.daos.interfaces.TblUserDAO;
import com.tientt.entities.TblQuiz;
import com.tientt.entities.TblTest;
import com.tientt.entities.TblUser;
import com.tientt.entities.TblQuizQuestion;
import com.tientt.requestobjects.SubmitRequestObject;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TblQuizBLOImpl implements TblQuizBLO, Serializable {
    private final TblQuizQuestionBLO quizQuestionBLO = TblQuizQuestionBLO.newInstance();
    private final TblQuizDAO quizDAO = TblQuizDAO.newInstance();
    private final TblUserDAO userDAO = TblUserDAO.newInstance();
    private final TblTestDAO testDAO = TblTestDAO.newInstance();

    @Override
    public TblQuiz createQuiz(String testID, String userEmail) throws
            IllegalArgumentException {
        //get Test and User
        TblTest test = testDAO.findTestByID(testID);
        TblUser user = userDAO.findUserByEmail(userEmail);

        if (test != null || user != null) {
            int numOfQuestion = test.getNumOfQuestion();
            long beginTime = new Date().getTime();
            long defaultEndTime = beginTime + test.getTestTimeLength() * 60 * 1000;
            TblQuiz quiz = new TblQuiz();
            quiz.setBeginTime(beginTime);
            quiz.setEndTime(defaultEndTime);
            quiz.setTest(test);
            quiz.setUser(user);
            quiz.setID(UUID.randomUUID().toString());
            quiz.setSubmitted(false);

            String subjectID = test.getSubject().getID();
            List<TblQuizQuestion> quizQuestionList = quizQuestionBLO.getNRandomQuizQuestionBySubjectID(numOfQuestion, subjectID);
            quiz.setQuizQuestionList(quizQuestionList);
            for (TblQuizQuestion quizQuestion : quizQuestionList) {
                quizQuestion.setQuiz(quiz);
            }
            quizDAO.insertQuiz(quiz);
            return quiz;
        }
        return null;
    }

    @Override
    public List<TblQuiz> findAllQuizByUserEmail(String userEmail) {
        return quizDAO.findAllQuizByUserEmail(userEmail);
    }

    @Override
    public List<TblQuiz> findAllQuizByUserEmailAndSubjectID(String userEmail, String subjectID) {
        return quizDAO.findAllQuizByUserEmailAndSubjectID(userEmail, subjectID);
    }

    @Override
    public TblQuiz findQuizByID(String quizID) {
        return quizDAO.findQuizByID(quizID);
    }


    @Override
    public float calculateMark(String quizID) {
        TblQuiz quiz = quizDAO.findQuizByID(quizID);
        if (quiz != null) {
            int numOfQuestion = quiz.getTest().getNumOfQuestion();
            int numOfCorrectAnswer = quizDAO.countCorrectAnswerByQuizID(quizID);
            return (((float) numOfCorrectAnswer) / numOfQuestion) * Constant.MARK_MAX_SCALE;
        }
        return 0.0f;
    }



    @Override
    public void submitQuiz(String quizID, List<SubmitRequestObject> submitRequestObjectList, long endTime) {
        TblQuiz quiz = quizDAO.findQuizByID(quizID);
        if (quiz != null) {
            quizQuestionBLO.updateSubmission(submitRequestObjectList);
            quiz.setEndTime(endTime);
            quiz.setSubmitted(true);
            quiz.setMark(calculateMark(quizID));
            quizDAO.updateQuiz(quiz);
        }//end if quiz is not null
    }

    @Override
    public void submitQuiz(String quizID, long endtime) {
        TblQuiz quiz = quizDAO.findQuizByID(quizID);
        if (quiz != null) {
            quiz.setSubmitted(true);
            quiz.setMark(calculateMark(quizID));
            quiz.setEndTime(endtime);
            quizDAO.updateQuiz(quiz);
        }//end if quiz is not null
    }

    @Override
    public TblQuiz findQuizByTestID(String testID) {
        return quizDAO.findQuizByTestID(testID);
    }

    @Override
    public TblQuiz findQuizByTestIDAndUserEmail(String testID, String userEmail) {
        return quizDAO.findQuizByTestIDAndUserEmail(testID, userEmail);
    }
}
