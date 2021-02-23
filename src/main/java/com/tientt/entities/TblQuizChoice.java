package com.tientt.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_QuizChoice")
public class TblQuizChoice implements Serializable {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "content")
    private String content;
    @Column(name = "isCorrect")
    private boolean isCorrect;
    @Column(name = "isSelect")
    private boolean isSelect;
    @ManyToOne
    @JoinColumn(name = "quizQuestionID", referencedColumnName = "ID")
    private TblQuizQuestion quizQuestion;

    public TblQuizChoice() {
    }

    public TblQuizChoice(String ID) {
        this.ID = ID;
    }

    public TblQuizChoice(String ID, String content, boolean isCorrect, boolean isSelect, TblQuizQuestion quizQuestion) {
        this.ID = ID;
        this.content = content;
        this.isCorrect = isCorrect;
        this.isSelect = isSelect;
        this.quizQuestion = quizQuestion;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public TblQuizQuestion getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(TblQuizQuestion quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    @Override
    public String toString() {
        return "TblQuizChoice{" +
                "ID='" + ID + '\'' +
                ", content='" + content + '\'' +
                ", isCorrect=" + isCorrect +
                ", isSelect=" + isSelect +
                '}';
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
        if (!(object instanceof TblQuizChoice)) {
            return false;
        }
        TblQuizChoice other = (TblQuizChoice) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }
}
