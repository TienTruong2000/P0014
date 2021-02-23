package com.tientt.blos.implementations;

import com.tientt.blos.interfaces.TblQuestionBLO;
import com.tientt.blos.interfaces.TblQuizQuestionBLO;
import com.tientt.daos.interfaces.TblQuizQuestionDAO;
import com.tientt.entities.TblChoice;
import com.tientt.entities.TblQuestion;
import com.tientt.entities.TblQuizChoice;
import com.tientt.entities.TblQuizQuestion;
import com.tientt.requestobjects.SubmitRequestObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TblQuizQuestionBLOImpl implements Serializable, TblQuizQuestionBLO {
    private final TblQuestionBLO questionBLO =  TblQuestionBLO.newInstance();
    private final TblQuizQuestionDAO quizQuestionDAO = TblQuizQuestionDAO.newInstance();

    @Override
    public List<TblQuizQuestion> getNRandomQuizQuestionBySubjectID(int numOfQuestion, String subjectID)
            throws IllegalArgumentException{
        List<TblQuestion> questionList =
                questionBLO.getListRandomActiveQuestionBySubjectID
                        (numOfQuestion, subjectID);
        //mapping Question to QuizQuestion
        List<TblQuizQuestion> quizQuestionList = new ArrayList<>();
        for (TblQuestion question: questionList){
            TblQuizQuestion quizQuestion = new TblQuizQuestion();
            quizQuestion.setContent(question.getContent());
            quizQuestion.setID(UUID.randomUUID().toString());
            //mapping Choice to QuizChoice
            List<TblQuizChoice> quizChoiceList = new ArrayList<>();
            for (TblChoice choice: question.getTblChoiceList()){
                TblQuizChoice quizChoice = new TblQuizChoice();
                quizChoice.setContent(choice.getContent());
                quizChoice.setID(UUID.randomUUID().toString());
                quizChoice.setCorrect(choice.isCorrect());
                quizChoice.setQuizQuestion(quizQuestion);
                quizChoiceList.add(quizChoice);
            }
            quizQuestion.setQuizChoiceList(quizChoiceList);
            quizQuestionList.add(quizQuestion);
        }
        return quizQuestionList;
    }

    @Override
    public void updateSubmission(List<SubmitRequestObject> requestObjectList) {
        for (SubmitRequestObject requestObject: requestObjectList){
            String questionID = requestObject.getQuestionID();
            TblQuizQuestion quizQuestion = quizQuestionDAO.findTblQuizQuestionByID(questionID);
            if (quizQuestion != null){
                for (TblQuizChoice quizChoice: quizQuestion.getQuizChoiceList()){
                    if (quizChoice.getID().equals(requestObject.getChoiceID())){
                        quizChoice.setSelect(true);
                    } else{
                        quizChoice.setSelect(false);
                    }
                }//end for iterate through quiz choice
                quizQuestionDAO.updateTblQuizQuestion(quizQuestion);
            }//end if quiz question not null
        }//end for iterate through requestObjectList

    }
}
