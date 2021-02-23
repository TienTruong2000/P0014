package com.tientt.validators;

import java.util.HashMap;
import java.util.Map;

public abstract class Validator<T> {

    Map<String, String> errors = new HashMap<>();
    T object;

    public Validator(T object) {
        this.object = object;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }

    public abstract void validateObject();

    public void addError(String errorCode, String message) {
        this.errors.put(errorCode, message);
    }
}
