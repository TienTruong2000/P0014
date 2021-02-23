package com.tientt.blos.implementations;

import com.tientt.blos.interfaces.TblUserBLO;
import com.tientt.commons.UserStatus;
import com.tientt.daos.interfaces.TblUserDAO;
import com.tientt.entities.TblUser;
import com.tientt.requestobjects.UserRequestObject;
import com.tientt.utils.HashingUtil;


public class TblUserBLOImpl implements TblUserBLO {
    private TblUserDAO userDAO = TblUserDAO.newInstance();

    @Override
    public TblUser findUserByEmailAndPassword(String email, String password) {
        String hashedPassword = HashingUtil.hashPassword(password);
        return userDAO.findUserByEmailAndPassword(email, hashedPassword);
    }

    @Override
    public int countUserByEmail(String email) {
        return userDAO.countUserByEmail(email);
    }

    private TblUser mappingFromRequestObject(UserRequestObject requestObject) {
        TblUser user = new TblUser();
        user.setEmail(requestObject.getEmail());
        user.setFullname(requestObject.getFullname());
        user.setStatus(UserStatus.USER_ACTIVATED);
        String hashedPassword = HashingUtil.hashPassword(requestObject.getPassword());
        user.setPassword(hashedPassword);
        return user;
    }

    @Override
    public void insertAccount(UserRequestObject requestObject) {
        TblUser user = mappingFromRequestObject(requestObject);
        userDAO.insertAccount(user);
    }
}
