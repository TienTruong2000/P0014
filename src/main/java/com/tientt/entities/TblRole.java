package com.tientt.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_Role")
public class TblRole implements Serializable {
    @Column(name = "ID")
    @Id
    private int ID;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public TblRole(int ID) {
        this.ID = ID;
    }

    public TblRole() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += this.ID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        //Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRole)) {
            return false;
        }
        TblRole other = (TblRole) object;
        if (this.ID!=other.getID()) {
            return false;
        }
        return true;
    }
}
