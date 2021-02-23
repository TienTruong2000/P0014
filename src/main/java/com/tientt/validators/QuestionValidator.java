package com.tientt.validators;

import com.tientt.requestobjects.ChoiceRequestObject;
import com.tientt.requestobjects.QuestionRequestObject;

import java.util.List;

public class QuestionValidator extends Validator<QuestionRequestObject> {
    public QuestionValidator(QuestionRequestObject requestObject) {
        super(requestObject);
    }

    private void checkContent() {
        String content = this.object.getContent();
        if (content.isEmpty()) {
            addError("content", "Content cannot be empty");
        } else if (content.length() > 1000) {
            addError("content", "Content cannot excess 1000 characters");
        }
    }

    private void checkChoiceContent() {
        List<ChoiceRequestObject> choiceList = object.getChoices();
        for (ChoiceRequestObject choice : choiceList) {
            String content = choice.getChoiceContent();
            if (content.isEmpty()) {
                addError("choice" + choice.getChoiceID(), "Content cannot be empty");
            } else if (content.length() > 1000) {
                addError("choice" + choice.getChoiceID(), "Content cannot excess 1000 characters");
            }
        }
    }




    @Override
    public void validateObject() {
        checkContent();
        checkChoiceContent();
    }
}
