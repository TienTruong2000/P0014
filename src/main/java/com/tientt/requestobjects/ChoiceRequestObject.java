package com.tientt.requestobjects;

import java.io.Serializable;

public class ChoiceRequestObject implements Serializable {
    private String choiceContent;
    private String choiceID;

    public ChoiceRequestObject() {
    }

    public String getChoiceContent() {
        return choiceContent;
    }

    public void setChoiceContent(String choiceContent) {
        this.choiceContent = choiceContent;
    }

    @Override
    public String toString() {
        return "ChoiceRequestObject{" +
                "choiceContent='" + choiceContent + '\'' +
                ", choiceID='" + choiceID + '\'' +
                '}';
    }

    public String getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(String choiceID) {
        this.choiceID = choiceID;
    }
}
