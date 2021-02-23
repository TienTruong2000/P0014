package com.tientt.blos.interfaces;

import com.tientt.blos.implementations.TblTestBLOImpl;
import com.tientt.entities.TblTest;
import com.tientt.requestobjects.TestRequestObject;

import java.text.ParseException;
import java.util.List;

public interface TblTestBLO {
    static TblTestBLO newInstance(){
        return new TblTestBLOImpl();
    }

    TblTest insertTest(TestRequestObject requestObject) throws ParseException, NumberFormatException;

    List<TblTest> findAllTestOrderByCreateDate();

    List<TblTest> findAllTestBySubjectOrderByCreateDate(String subjectID);

    TblTest findTestByID(String testID);

}
