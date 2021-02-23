package com.tientt.validators;

import com.tientt.blos.interfaces.TblUserBLO;
import com.tientt.requestobjects.UserRequestObject;

public class UserValidator extends Validator<UserRequestObject> {

    public UserValidator(UserRequestObject object) {
        super(object);
    }

    private void checkPassword() {
        if (object.getPassword().isEmpty()) {
            addError("password", "Password cannot be null");
        } else if (object.getPassword().length() < 8) {
            addError("password", "Password cannot shorted than 8 characters");
        }
    }

    private void checkConfirmedPassword() {
        String password = object.getPassword();
        String confirmedPassword = object.getConfirmedPassword();
        if (!password.equals(confirmedPassword)) {
            addError("confirmedPassword", "Confirm password mismatch");
        }
    }

    private void checkFullName() {
        String fullname = object.getFullname();
        if (fullname.isEmpty()) {
            addError("fullname", "Fullname cannot null");
        } else if (fullname.length() > 150) {
            addError("fullname", "Fullname cannot be more than 150 characters");
        }
    }

    private void checkEmail() {
        String email = object.getEmail();
        TblUserBLO userBLO = TblUserBLO.newInstance();
        if (email.isEmpty()) {
            addError("email", "Email cannot null");
        } else if (email.length() > 320) {
            addError("email", "Email cannot have more than 320 characters");
        } else if (!email.matches("^(\\w*\\.)*\\w+@(\\w)+(\\w*\\.)+(\\w)*$")) {
            addError("email", "Invalid email format");
        } else if (userBLO.countUserByEmail(email) != 0) {
            addError("email", "Email already existed");
        }
    }

    @Override
    public void validateObject() {
        this.checkFullName();
        this.checkConfirmedPassword();
        this.checkPassword();
        this.checkEmail();
    }
}
