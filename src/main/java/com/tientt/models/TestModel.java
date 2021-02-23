package com.tientt.models;

import com.tientt.commons.TestStatus;
import com.tientt.entities.TblQuiz;
import com.tientt.entities.TblSubject;

import java.io.Serializable;

public class TestModel implements Serializable {
    private String ID;
    private String name;
    private long openTime;
    private long deadline;
    private int length;
    private int numOfQuestion;
    private TblSubject subject;
    private TestStatus status;
    private TblQuiz quiz;

    public TestModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public TblQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(TblQuiz quiz) {
        this.quiz = quiz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    public TblSubject getSubject() {
        return subject;
    }

    public void setSubject(TblSubject subject) {
        this.subject = subject;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", openTime=" + openTime +
                ", deadline=" + deadline +
                ", length=" + length +
                ", numOfQuestion=" + numOfQuestion +
                ", subject=" + subject +
                ", status=" + status +
                ", quiz=" + quiz +
                '}';
    }


}
