package com.tientt.blos.implementations;

import com.tientt.blos.interfaces.TblTestBLO;
import com.tientt.commons.Constant;
import com.tientt.daos.interfaces.TblSubjectDAO;
import com.tientt.daos.interfaces.TblTestDAO;
import com.tientt.entities.TblSubject;
import com.tientt.entities.TblTest;
import com.tientt.requestobjects.TestRequestObject;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TblTestBLOImpl implements Serializable, TblTestBLO {
    private final TblTestDAO testDAO = TblTestDAO.newInstance();
    private final TblSubjectDAO subjectDAO = TblSubjectDAO.newInstance();

    private TblTest mappingFromRequestObject(TestRequestObject requestObject) throws ParseException,
            NumberFormatException {
        TblTest tblTest = new TblTest();
        tblTest.setName(requestObject.getName());

        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_TIME_FORMAT);
        Date date = format.parse(requestObject.getOpenTime());
        tblTest.setOpenTime(date.getTime());
        if (!requestObject.getDeadlineTime().isEmpty()) {
            date = format.parse(requestObject.getDeadlineTime());
            tblTest.setDeadlineTime(date.getTime());
        }

        tblTest.setTestTimeLength(Integer.parseInt(requestObject.getTestTimeLength()));
        tblTest.setNumOfQuestion(Integer.parseInt(requestObject.getNumOfQuestion()));
        return tblTest;
    }

    @Override
    public TblTest insertTest(TestRequestObject requestObject) throws ParseException, NumberFormatException {
        TblTest test = mappingFromRequestObject(requestObject);
        //set attribute for test
        test.setID(UUID.randomUUID().toString());
        test.setCreateDate(new Date().getTime());
        TblSubject subject = subjectDAO.findTblSubjectByID(requestObject.getSubjectID());
        test.setSubject(subject);
        testDAO.insertTest(test);
        return test;
    }

    @Override
    public List<TblTest> findAllTestOrderByCreateDate() {
        return testDAO.findAllTestOrderByCreateDate();
    }

    @Override
    public List<TblTest> findAllTestBySubjectOrderByCreateDate(String subjectID) {
        return testDAO.findTestBySubjectOrderByCreateDate(subjectID);
    }

    @Override
    public TblTest findTestByID(String testID) {
        return testDAO.findTestByID(testID);
    }
}
