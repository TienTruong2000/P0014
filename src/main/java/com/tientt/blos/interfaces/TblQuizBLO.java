package com.tientt.blos.interfaces;

import com.tientt.blos.implementations.TblQuizBLOImpl;
import com.tientt.entities.TblQuiz;
import com.tientt.requestobjects.SubmitRequestObject;

import java.util.List;

public interface TblQuizBLO {
    static TblQuizBLO newInstance(){
        return new TblQuizBLOImpl();
    }

    TblQuiz createQuiz(String testID, String userEmail);

    List<TblQuiz> findAllQuizByUserEmail(String userEmail);

    List<TblQuiz> findAllQuizByUserEmailAndSubjectID(String userEmail, String subjectID);

    TblQuiz findQuizByID(String quizID);

    float calculateMark(String quizID);


    void submitQuiz(String quizID, List<SubmitRequestObject> submitRequestObjectList, long endTime);

    void submitQuiz(String quizID, long endTime);

    TblQuiz findQuizByTestID(String testID);

    TblQuiz findQuizByTestIDAndUserEmail(String testID, String userEmail);

}
