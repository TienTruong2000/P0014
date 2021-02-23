package com.tientt.blos.interfaces;

import com.tientt.blos.implementations.TblQuizQuestionBLOImpl;
import com.tientt.entities.TblQuizQuestion;
import com.tientt.requestobjects.SubmitRequestObject;

import java.util.List;

public interface TblQuizQuestionBLO {
    static TblQuizQuestionBLO newInstance(){
        return new TblQuizQuestionBLOImpl();
    }

    List<TblQuizQuestion> getNRandomQuizQuestionBySubjectID(int numOfQuestion, String subjectID)
            throws IllegalArgumentException;

    void updateSubmission(List<SubmitRequestObject> requestObjectList);

}
