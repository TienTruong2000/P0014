package com.tientt.blos.interfaces;

import com.tientt.blos.implementations.TblUserBLOImpl;
import com.tientt.entities.TblUser;
import com.tientt.requestobjects.UserRequestObject;


public interface TblUserBLO {

    static TblUserBLO newInstance() {
        return new TblUserBLOImpl();
    }

    TblUser findUserByEmailAndPassword(String username, String password);

    int countUserByEmail(String email);

    void insertAccount(UserRequestObject requestObject);

}
