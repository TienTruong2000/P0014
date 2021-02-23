package com.tientt.daos.interfaces;

import com.tientt.daos.implementations.TblQuizDAOImpl;
import com.tientt.entities.TblQuiz;

import java.util.List;

public interface TblQuizDAO {
    static TblQuizDAO newInstance(){
        return new TblQuizDAOImpl();
    }

    TblQuiz findQuizByTestID(String testID);

    TblQuiz findQuizByID(String quizID);

    TblQuiz findQuizByTestIDAndUserEmail(String testID, String userEmail);

    List<TblQuiz> findAllQuizByUserEmail(String email);

    List<TblQuiz> findAllQuizByUserEmailAndSubjectID(String userEmail, String subjectID);

    int countCorrectAnswerByQuizID(String quizID);

    void updateQuiz(TblQuiz quiz);

    void insertQuiz(TblQuiz quiz);
}
