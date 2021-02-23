package com.tientt.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_Choice")
public class TblChoice implements Serializable {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private int status;
    @Column(name = "isCorrect")
    private boolean correct;
    @JoinColumn(name = "questionID", referencedColumnName = "ID")
    @ManyToOne
    private TblQuestion question;

    public TblChoice() {
    }

    public TblChoice(String ID) {
        this.ID = ID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public TblQuestion getQuestion() {
        return question;
    }

    public void setQuestion(TblQuestion question) {
        this.question = question;
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
        if (!(object instanceof TblChoice)) {
            return false;
        }
        TblChoice other = (TblChoice) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TblChoice{" +
                "ID='" + ID + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", correct=" + correct +
                ", question=" + question +
                '}';
    }
}
