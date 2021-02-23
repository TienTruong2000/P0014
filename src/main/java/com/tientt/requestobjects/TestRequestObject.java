package com.tientt.requestobjects;

import java.io.Serializable;

public class TestRequestObject implements Serializable {
    
    private String name;
    private String openTime;
    private String deadlineTime;
    private String testTimeLength;
    private String numOfQuestion;
    private String subjectID;

    public TestRequestObject() {
    }

    public TestRequestObject(String name, String openTime, String deadlineTime, String testTimeLength, String numOfQuestion, String subjectID) {
        this.name = name;
        this.openTime = openTime;
        this.deadlineTime = deadlineTime;
        this.testTimeLength = testTimeLength;
        this.numOfQuestion = numOfQuestion;
        this.subjectID = subjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getTestTimeLength() {
        return testTimeLength;
    }

    public void setTestTimeLength(String testTimeLength) {
        this.testTimeLength = testTimeLength;
    }

    public String getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(String numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }
}
