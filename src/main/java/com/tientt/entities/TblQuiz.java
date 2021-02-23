package com.tientt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "tbl_Quiz")
public class TblQuiz implements Serializable {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "beginTime")
    private long beginTime;
    @Column(name = "endTime")
    private long endTime;
    @ManyToOne
    @JoinColumn(name = "testID", referencedColumnName = "ID")
    private TblTest test;
    @ManyToOne
    @JoinColumn(name = "studentEmail", referencedColumnName = "email")
    private TblUser user;
    @Column(name = "mark")
    private float mark;
    @Column(name = "isSubmitted")
    private boolean isSubmitted;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<TblQuizQuestion> quizQuestionList;


    public TblQuiz() {
    }

    public TblQuiz(String ID) {
        this.ID = ID;
    }

    public TblQuiz(String ID, long beginTime, long endTime,
                   TblTest test, TblUser user, float mark) {
        this.ID = ID;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.test = test;
        this.user = user;
        this.mark = mark;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public TblTest getTest() {
        return test;
    }

    public void setTest(TblTest test) {
        this.test = test;
    }

    public TblUser getUser() {
        return user;
    }

    public void setUser(TblUser user) {
        this.user = user;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public List<TblQuizQuestion> getQuizQuestionList() {
        return quizQuestionList;
    }

    public void setQuizQuestionList(List<TblQuizQuestion> quizQuestionList) {
        this.quizQuestionList = quizQuestionList;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.ID != null ? this.ID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        //Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblQuiz)) {
            return false;
        }
        TblQuiz other = (TblQuiz) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TblQuiz{" +
                "ID='" + ID + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", test=" + test +
                ", user=" + user +
                ", mark=" + mark +
                ", isSubmitted=" + isSubmitted +
                ", quizQuestionList=" + quizQuestionList +
                '}';
    }
}
