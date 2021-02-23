package com.tientt.daos.interfaces;

import com.tientt.daos.implementations.TblQuestionDAOImpl;
import com.tientt.entities.TblQuestion;

import java.util.List;

public interface TblQuestionDAO {
    static TblQuestionDAO newInstance() {
        return new TblQuestionDAOImpl();
    }

    List<TblQuestion> findAllQuestionByPageOrderByCreateDate(int page);

    int countPage();

    List<TblQuestion> findQuestionByContentAndSubjectAndStatusAndPageOrderByCreateDate
            (String content, String subjectID, String statusString, int page);

    int countPageByContentAndSubjectAndStatus(String content, String subjectID, String statusString);

    void updateTblQuestion(TblQuestion question);

    void insertTblQuestion(TblQuestion question);

    TblQuestion findTblQuestionByID(String questionID);

    int countNumberOfQuestionBySubjectIDAndStatus(String subjectID, int status);

    TblQuestion getTblQuestionBySubjectIDAndStatusAndIndex(String subjectID, int status, int index);

}
