package com.tientt.daos.interfaces;

import com.tientt.daos.implementations.TblUserDAOImpl;
import com.tientt.entities.TblUser;

public interface TblUserDAO {
    static TblUserDAO newInstance(){
        return new TblUserDAOImpl();
    }

    TblUser findUserByEmailAndPassword(String username, String password);

    int countUserByEmail(String email);

    void insertAccount(TblUser user);

    TblUser findUserByEmail(String email);
}
