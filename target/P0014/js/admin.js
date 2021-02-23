function removeError() {
    const errorElements = document.getElementsByClassName("error");
    while (errorElements.length > 0) {
        errorElements[0].parentNode.removeChild(errorElements[0]);
    }
}

function updateQuestion(id, button) {
    removeError();
    let tableElement = document.getElementById(id);
    let subjectIDSelect = tableElement.querySelector("select[name='uSubjectID']");
    let statusSelect = tableElement.querySelector("select[name='uStatus']")
    let contentInput = tableElement.querySelector("input[name='uContent']");
    let choiceElements = tableElement.querySelectorAll("tr[name='choice']");
    let correctElementName = "correct_"+id;
    let correctElement = tableElement.querySelector("input[name='"+ correctElementName+"']:checked");

    let hasError = !isContentValid(contentInput);

    let choiceList = [];
    for (let i = 0; i < choiceElements.length; i++){
        let choiceElement = choiceElements[i];
        let choiceID = choiceElement.id;
        let choiceContent = choiceElement.querySelector("input[name='choiceContent']");
        let choice = {
            choiceID : choiceID,
            choiceContent: choiceContent.value,
        }
        choiceList.push(choice);
        if (!hasError){
            hasError = !isContentValid(choiceContent);
        }
    }

    if (!hasError){
        let question = {
            questionID: id,
            subjectID: subjectIDSelect.value,
            status: statusSelect.value,
            content: contentInput.value,
            choices: choiceList,
            correctChoiceID: correctElement.value
        }
        let request = new XMLHttpRequest();
        let url = 'UpdateAction';
        request.open("POST", url, true);
        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200){
                    alert("Update successfully");
                }else{
                    console.log(request.responseText);
                }
            }
        }
        request.send(JSON.stringify(question));
    }
}

function isContentValid(node){
    if (node.value.trim().length > 1000 || node.value.trim().length === 0) {
        showError(node, 'Content invalid length. Must between 1 and 1000 characters');
        return false;
    }
    return true;
}

function showError(node, message){
    let errorElement = document.createElement("div");
    errorElement.style.color = "red";
    errorElement.className = "error";
    errorElement.innerText = message;
    node.parentNode.insertBefore(errorElement, node.nextSibling);
}


function validateCreateQuestionForm(){
    removeError();
    let tableElement = document.getElementById("createTable");
    let contentInput = tableElement.querySelector("input[name='cQuestionContent']");
    let choiceElements = tableElement.querySelectorAll("input[class='choiceContent']");
    let correctElement = tableElement.querySelector("input[name='correct']:checked");
    let hasError = !isContentValid(contentInput);
    console.log(choiceElements);
    for (let i = 0; i < choiceElements.length; i++){
        let choiceElement =  choiceElements[i];
        hasError = !isContentValid(choiceElement);
    }
    if (correctElement == null){
        hasError = true;
        showError(tableElement, 'Please choose one correct answer');
    }
    return !hasError;
}


function validateCreateTestForm(formElement){
    removeError();
    let nameElement = formElement.querySelector("input[name='cTestName']");
    let openTimeElement = formElement.querySelector("input[name='cTestOpenTime']");
    let deadlineTimeElement = formElement.querySelector("input[name='cTestDeadlineTime']");
    let testTimeElement = formElement.querySelector("input[name='cTestTimeLength']");
    let noOfQuestionElement = formElement.querySelector("input[name='cTestNumOfQuestion']");
    let hasError = false;

    if (nameElement.value.trim().length ===0 || nameElement.value.trim().length > 150){
        showError(nameElement, "Name can only contains 1 to 150 characters");
        hasError = true;
    }
    if (Date.parse(openTimeElement.value.trim()) == null){
        showError(openTimeElement, "Please enter datetime format dd/MM/yyyy hh:mm");
        hasError = true;
    }
    if (Date.parse(deadlineTimeElement.value.trim()) == null){
        showError(deadlineTimeElement, "Please enter datetime format dd/MM/yyyy hh:mm");
        hasError = true;
    }
    if (isNaN(parseInt(testTimeElement.value.trim()))){
        showError(testTimeElement, "Please enter a number");
        hasError = true;

    } else{
        if (parseInt(testTimeElement.value.trim())<0){
            showError(testTimeElement, "Test time must bigger than 1 minute");
            hasError = true;
        }
    }
    if (!hasError){
        //at this point deadline and opentime must be valid to check
        let deadline = Date.parse(deadlineTimeElement.value.trim()).getTime();
        let openTime = Date.parse(openTimeElement.value.trim()).getTime();
        let testLength = parseInt(testTimeElement.value.trim());
        if (deadline < openTime + testLength * 60 * 1000){
            hasError = true;
            showError(deadlineTimeElement, "Deadline must bigger than open time + test length")
        }
    }

    if (isNaN(parseInt(noOfQuestionElement.value))){
        showError(noOfQuestionElement, "Please enter a number");
        hasError = true;
    } else {
        if (parseInt(noOfQuestionElement.value)<1){
            showError(noOfQuestionElement, "Number of question cannot smaller than 1");
            hasError = true;
        }
    }
    return !hasError;
}

