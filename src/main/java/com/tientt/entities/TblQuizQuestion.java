package com.tientt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_QuizQuestion")
public class TblQuizQuestion implements Serializable {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "quizID", referencedColumnName = "ID")
    private TblQuiz quiz;
    @OneToMany(mappedBy = "quizQuestion", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<TblQuizChoice> quizChoiceList;

    public TblQuizQuestion() {
    }

    public TblQuizQuestion(String ID) {
        this.ID = ID;
    }

    public TblQuizQuestion(String ID, String content, TblQuiz quiz) {
        this.ID = ID;
        this.content = content;
        this.quiz = quiz;
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

    public TblQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(TblQuiz quiz) {
        this.quiz = quiz;
    }

    public List<TblQuizChoice> getQuizChoiceList() {
        return quizChoiceList;
    }

    public void setQuizChoiceList(List<TblQuizChoice> quizChoiceList) {
        this.quizChoiceList = quizChoiceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.ID != null ? this.ID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblQuizQuestion)) {
            return false;
        }
        TblQuizQuestion other = (TblQuizQuestion) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TblQuizQuestion{" +
                "ID='" + ID + '\'' +
                ", content='" + content + '\'' +
                ", quizChoiceList=" + quizChoiceList +
                '}';
    }
}
