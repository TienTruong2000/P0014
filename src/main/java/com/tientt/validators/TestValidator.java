package com.tientt.validators;

import com.tientt.blos.interfaces.TblQuestionBLO;
import com.tientt.commons.Constant;
import com.tientt.requestobjects.TestRequestObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestValidator extends Validator<TestRequestObject> {
    private final TblQuestionBLO questionBLO = TblQuestionBLO.newInstance();


    public TestValidator(TestRequestObject object) {
        super(object);
    }

    private void checkNumOfQuestion() {
        try {
            int numOfQuestion = Integer.parseInt(object.getNumOfQuestion());
            if (numOfQuestion < 1) {
                addError("NumOfQuestion", "Number of question cannot smaller than 1");
            }//end if numOfQuestion < 1
            else {
                int questionCount = questionBLO.getNumberOfActiveQuestionBySubjectID
                        (object.getSubjectID());
                if (numOfQuestion > questionCount) {
                    this.addError("NumOfQuestion",
                            "Not enough question in question bank. Maximum " + questionCount + " question(s)");
                }
            }
        } catch (NumberFormatException e) {
            this.addError("NumOfQuestion",
                    "Number of question must be a positive number");
        }
    }

    private void checkOpenTime() {
        try {
            SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_TIME_FORMAT);
            format.setLenient(false);
            format.parse(object.getOpenTime());
        } catch (ParseException ex) {
            this.addError("openTime", "Please enter datetime format "+Constant.DATE_TIME_FORMAT);
        }
    }

    private void checkDeadlineTime() {
        try {
            SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_TIME_FORMAT);
            format.setLenient(false);
            format.parse(object.getDeadlineTime());
        } catch (ParseException ex) {
            this.addError("deadlineTime", "Please enter datetime format "+Constant.DATE_TIME_FORMAT);
        }
    }

    private void checkTestTimeLength() {
        try {
            int timeLength = Integer.parseInt(object.getTestTimeLength());
            if (timeLength < 1) {
                this.addError("testTimeLength",
                        "Please input time > 1 minute");
            }
        } catch (NumberFormatException ex) {
            this.addError("testTimeLength",
                    "Please input time > 1 minute");
        }
    }

    @Override
    public void validateObject() {
        checkDeadlineTime();
        checkNumOfQuestion();
        checkOpenTime();
        checkTestTimeLength();
    }
}
