package com.tientt.entities;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_User")
public class TblUser implements Serializable {
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "status")
    private int status;
    @JoinColumn(name="roleID", referencedColumnName = "ID")
    @ManyToOne
    private TblRole role;

    public TblUser() {
    }

    public TblUser(String email) {
        this.email = email;
    }

    public TblUser(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TblRole getRole() {
        return role;
    }

    public void setRole(TblRole role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        //Warning - this method won't work in the case the email fields are not set
        if (!(object instanceof TblUser)) {
            return false;
        }
        TblUser other = (TblUser) object;
        if ((this.email == null && other.getEmail() != null) || (this.email != null && !this.email.equals(other.getEmail()))) {
            return false;
        }
        return true;
    }
}
