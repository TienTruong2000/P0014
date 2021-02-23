package com.tientt.blos.implementations;

import com.tientt.blos.interfaces.TblQuestionBLO;
import com.tientt.commons.Constant;
import com.tientt.commons.QuestionStatus;
import com.tientt.daos.interfaces.TblQuestionDAO;
import com.tientt.daos.interfaces.TblSubjectDAO;
import com.tientt.entities.TblChoice;
import com.tientt.entities.TblQuestion;
import com.tientt.entities.TblSubject;
import com.tientt.entities.TblTest;
import com.tientt.requestobjects.ChoiceRequestObject;
import com.tientt.requestobjects.QuestionRequestObject;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.Date;

public class TblQuestionBLOImpl implements TblQuestionBLO, Serializable {
    private final TblQuestionDAO questionDAO = TblQuestionDAO.newInstance();
    private final TblSubjectDAO subjectDAO = TblSubjectDAO.newInstance();


    @Override
    public List<TblQuestion> findQuestionByContentAndSubjectIDAndStatusStringAndPage(String content, String subjectID, String statusString, int page) {

        if (content == null || subjectID == null || statusString == null){
             return questionDAO.findAllQuestionByPageOrderByCreateDate(page);
        } else {
            return questionDAO.findQuestionByContentAndSubjectAndStatusAndPageOrderByCreateDate
                    (content, subjectID, statusString, page);
        }

    }

    @Override
    public int getMaxPageByContentAndSubjectIDAndStatusStringAndPage(String content, String subjectID, String statusString) {
        int recordNum;
        if (content == null || subjectID == null || statusString == null){
            recordNum = questionDAO.countPage();
        } else {
            recordNum = questionDAO.countPageByContentAndSubjectAndStatus(content, subjectID, statusString);
        }
        int maxPage = recordNum / Constant.PAGE_SIZE;
        if (recordNum % Constant.PAGE_SIZE != 0) {
            maxPage++;
        }
        return maxPage;
    }


    private TblChoice mappingFromChoiceRequestObject(ChoiceRequestObject requestObject, boolean isCorrect) {
        TblChoice choice = new TblChoice();
        choice.setContent(requestObject.getChoiceContent());
        choice.setCorrect(isCorrect);
        choice.setID(requestObject.getChoiceID());
        return choice;
    }



    @Override
    public TblQuestion updateQuestion(QuestionRequestObject requestObject) {
        TblQuestion question = questionDAO.findTblQuestionByID(requestObject.getQuestionID());
        TblSubject subject = subjectDAO.findTblSubjectByID(requestObject.getSubjectID());
        if (question != null && subject != null) {
            //set question attributes
            question.setContent(requestObject.getContent());
            question.setStatus(requestObject.getStatus());
            question.setSubject(subject);
            //set question choice
            List<TblChoice> choiceList = new ArrayList<>();
            for (ChoiceRequestObject choiceRequestObject : requestObject.getChoices()) {
                boolean isCorrect = choiceRequestObject.getChoiceID().equals(requestObject.getCorrectChoiceID());
                TblChoice choiceEntity = mappingFromChoiceRequestObject(choiceRequestObject, isCorrect);
                choiceList.add(choiceEntity);
                choiceEntity.setQuestion(question);
            }
            question.setTblChoiceList(choiceList);
            //update question
            questionDAO.updateTblQuestion(question);
            return question;
        }//end if question and subject entity is not null
        return null;
    }



    @Override
    public TblQuestion insertQuestion(QuestionRequestObject requestObject) {
        TblQuestion question = new TblQuestion();
        TblSubject subject = subjectDAO.findTblSubjectByID(requestObject.getSubjectID());

        if (subject != null){
            //set question attribute
            question.setID(UUID.randomUUID().toString());
            question.setStatus(QuestionStatus.ACTIVATE);
            question.setContent(requestObject.getContent());
            question.setCreateDate(new Date().getTime());
            question.setSubject(subject);

            //set question choice
            List<TblChoice> choiceList = new ArrayList<>();
            List<ChoiceRequestObject> choiceRequestObjectList = requestObject.getChoices();
            int correctChoiceIndex = Integer.parseInt(requestObject.getCorrectChoiceID());
            for (int i = 0; i < Constant.QUESTION_MAX_CHOICE; i++) {
                ChoiceRequestObject choiceRequestObject = choiceRequestObjectList.get(i);
                boolean isCorrect = i == correctChoiceIndex;
                TblChoice choice = new TblChoice();
                choice.setContent(choiceRequestObject.getChoiceContent());
                choice.setCorrect(isCorrect);
                choice.setID(UUID.randomUUID().toString());
                choiceList.add(choice);
                choice.setQuestion(question);
            }
            question.setTblChoiceList(choiceList);
            questionDAO.insertTblQuestion(question);
            return question;
        }//end if subject is not null
        return null;
    }

    private List<Integer> getListRandomNumber(int n, int max){
        List<Integer> result = new ArrayList<>();
        int rand;
        Random random = new Random();
        while (result.size() < n){
            do {
                rand = random.nextInt(max);
            } while (result.contains(rand));
            result.add(rand);
        }
        return result;
    }

    @Override
    public List<TblQuestion> getListRandomActiveQuestionBySubjectID
            (int numOfQuestion, String subjectID)
            throws IllegalArgumentException{
        int questionCount = questionDAO.countNumberOfQuestionBySubjectIDAndStatus(subjectID, QuestionStatus.ACTIVATE);
        if (numOfQuestion > questionCount){
            throw new IllegalArgumentException("Not enough question in database");
        }
        List<Integer> randomIndexList = getListRandomNumber(numOfQuestion, questionCount);
        List<TblQuestion> questionList = new ArrayList<>();
        for (Integer index: randomIndexList){
            TblQuestion question =
                    questionDAO.getTblQuestionBySubjectIDAndStatusAndIndex(subjectID, QuestionStatus.ACTIVATE, index);
            questionList.add(question);
        }
        return questionList;

    }

    @Override
    public int getNumberOfActiveQuestionBySubjectID(String subjectID) {
        return questionDAO.countNumberOfQuestionBySubjectIDAndStatus(subjectID, QuestionStatus.ACTIVATE);
    }

    @Override
    public boolean isEnoughQuestionForTest(TblTest test) {
        String subjectID = test.getSubject().getID();
        int numOfQuestion = test.getNumOfQuestion();
        int availableQuestion = questionDAO.countNumberOfQuestionBySubjectIDAndStatus(subjectID, QuestionStatus.ACTIVATE);
        return numOfQuestion <= availableQuestion;
    }
}
