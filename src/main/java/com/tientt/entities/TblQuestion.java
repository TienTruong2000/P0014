package com.tientt.entities;



import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_Question")
public class TblQuestion implements Serializable {
    @Id
    @Column(name = "ID")
    @OrderColumn
    private String ID;
    @Column(name = "content")
    private String content;
    @Column(name = "createDate")
    private long createDate;
    @Column(name = "status")
    private int status;
    @JoinColumn(name = "subjectID", referencedColumnName = "ID")
    @ManyToOne
    private TblSubject subject;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TblChoice> tblChoiceList;

    public TblQuestion() {
    }

    public TblQuestion(String ID) {
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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TblSubject getSubject() {
        return subject;
    }

    public void setSubject(TblSubject subject) {
        this.subject = subject;
    }

    public List<TblChoice> getTblChoiceList() {
        return tblChoiceList;
    }

    public void setTblChoiceList(List<TblChoice> tblChoiceList) {
        this.tblChoiceList = tblChoiceList;
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
        if (!(object instanceof TblQuestion)) {
            return false;
        }
        TblQuestion other = (TblQuestion) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }

}
