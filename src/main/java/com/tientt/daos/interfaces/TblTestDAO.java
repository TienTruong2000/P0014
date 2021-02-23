package com.tientt.daos.interfaces;

import com.tientt.daos.implementations.TblTestDAOImpl;
import com.tientt.entities.TblTest;

import java.util.List;

public interface TblTestDAO {
    static TblTestDAO newInstance(){
        return new TblTestDAOImpl();
    }

    void insertTest(TblTest test);

    List<TblTest> findAllTestOrderByCreateDate();

    List<TblTest> findTestBySubjectOrderByCreateDate(String subjectID);

    TblTest findTestByID(String ID);
}
