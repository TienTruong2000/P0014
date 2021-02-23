package com.tientt.requestobjects;

public class UserRequestObject {
    private String email;
    private String password;
    private String confirmedPassword;
    private String fullname;

    public UserRequestObject() {
    }

    public UserRequestObject(String email, String password, String confirmedPassword, String fullname) {
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
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

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "UserRequestObject{" + "email=" + email + ", passsword=" + password + ", confirmedPassword=" + confirmedPassword + ", fullname=" + fullname + '}';
    }
}
