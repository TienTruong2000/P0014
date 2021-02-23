package com.tientt.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_Test")
public class TblTest implements Serializable {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "name")
    private String name;
    @Column(name = "createDate")
    private long createDate;
    @Column(name = "openTime")
    private long openTime;
    @Column(name = "deadlineTime")
    private long deadlineTime;
    @Column(name = "testTimeLength")
    private int testTimeLength;
    @Column(name = "numOfQuestion")
    private int numOfQuestion;
    @ManyToOne
    @JoinColumn(name = "subjectID", referencedColumnName = "ID")
    private TblSubject subject;

    public TblTest() {
    }

    public TblTest(String ID) {
        this.ID = ID;
    }

    public TblTest(String ID, String name, long createDate, long openTime, long deadlineTime, int testTimeLength, int numOfQuestion) {
        this.ID = ID;
        this.name = name;
        this.createDate = createDate;
        this.openTime = openTime;
        this.deadlineTime = deadlineTime;
        this.testTimeLength = testTimeLength;
        this.numOfQuestion = numOfQuestion;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(long deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public int getTestTimeLength() {
        return testTimeLength;
    }

    public void setTestTimeLength(int testTimeLength) {
        this.testTimeLength = testTimeLength;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ID != null ? ID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblTest)) {
            return false;
        }
        TblTest other = (TblTest) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "TblTest{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", openTime=" + openTime +
                ", deadlineTime=" + deadlineTime +
                ", testTimeLength=" + testTimeLength +
                ", numOfQuestion=" + numOfQuestion +
                ", subject=" + subject +
                '}';
    }
}
