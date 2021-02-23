package com.tientt.daos.interfaces;

import com.tientt.daos.implementations.TblQuizQuestionDAOImpl;
import com.tientt.entities.TblQuizQuestion;

public interface TblQuizQuestionDAO {
    static TblQuizQuestionDAO newInstance(){
        return new TblQuizQuestionDAOImpl();
    }

    TblQuizQuestion findTblQuizQuestionByID(String ID);

    void updateTblQuizQuestion(TblQuizQuestion quizQuestion);

}
