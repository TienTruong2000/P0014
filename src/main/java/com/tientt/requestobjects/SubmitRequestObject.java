package com.tientt.requestobjects;

import java.io.Serializable;

public class SubmitRequestObject implements Serializable {
    private String questionID;
    private String choiceID;

    public SubmitRequestObject() {
    }

    public SubmitRequestObject(String questionID, String choiceID) {
        this.questionID = questionID;
        this.choiceID = choiceID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(String choiceID) {
        this.choiceID = choiceID;
    }

    @Override
    public String toString() {
        return "SubmitRequestObject{" +
                "questionID='" + questionID + '\'' +
                ", choiceID='" + choiceID + '\'' +
                '}';
    }
}
