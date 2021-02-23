package com.tientt.requestobjects;

import java.util.List;

public class QuestionRequestObject {
    private String questionID;
    private String subjectID;
    private String content;
    private int status;
    private String correctChoiceID;
    private List<ChoiceRequestObject> choices;

    public QuestionRequestObject() {
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCorrectChoiceID() {
        return correctChoiceID;
    }

    public void setCorrectChoiceID(String correctChoiceID) {
        this.correctChoiceID = correctChoiceID;
    }

    public List<ChoiceRequestObject> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceRequestObject> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "QuestionRequestObject{" +
                "questionID='" + questionID + '\'' +
                ", subjectID='" + subjectID + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", correctChoiceID='" + correctChoiceID + '\'' +
                ", choices=" + choices +
                '}';
    }
}
