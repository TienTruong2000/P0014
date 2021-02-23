package com.tientt.blos.interfaces;

import com.tientt.blos.implementations.TblQuestionBLOImpl;
import com.tientt.entities.TblQuestion;
import com.tientt.entities.TblTest;
import com.tientt.requestobjects.QuestionRequestObject;

import java.util.List;

public interface TblQuestionBLO{
    static TblQuestionBLO newInstance(){
        return new TblQuestionBLOImpl();
    }

    List<TblQuestion> findQuestionByContentAndSubjectIDAndStatusStringAndPage
            (String content, String subjectID, String statusString, int page);

    int getMaxPageByContentAndSubjectIDAndStatusStringAndPage
            (String content, String subjectID, String statusString);

    TblQuestion updateQuestion(QuestionRequestObject requestObject);

    TblQuestion insertQuestion(QuestionRequestObject requestObject);

    List<TblQuestion> getListRandomActiveQuestionBySubjectID
            (int numOfQuestion, String subjectID)
            throws IllegalArgumentException;

    int getNumberOfActiveQuestionBySubjectID(String subjectID);

    boolean isEnoughQuestionForTest(TblTest test);



}
