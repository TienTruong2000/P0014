package com.tientt.commons;

public class TestStatus {
    public static final TestStatus CLOSED = new TestStatus(1, "Closed");
    public static final TestStatus OPENED = new TestStatus(2, "Opened");
    public static final TestStatus NOT_OPEN = new TestStatus(3, "Not opened");
    public static final TestStatus IS_SUBMITTED = new TestStatus(4, "Is submitted");
    public static final TestStatus ON_GOING = new TestStatus(5, "On going");


    private final int statusCode;
    private final String name;


    private TestStatus(int statusCode, String name) {
        this.statusCode = statusCode;
        this.name = name;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getName() {
        return name;
    }


}
