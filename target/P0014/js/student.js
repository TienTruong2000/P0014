function checkTakeTest(formElement){
    let deadlineElement = formElement.querySelector("input[name='deadline']");
    let deadline = parseInt(deadlineElement.value);
    let testID =  formElement.querySelector("input[name='testID']").value;
    if (Date.now() > deadline){
        window.location = window.location.href;
    } else{
        let request = new XMLHttpRequest();
        let url = "GetQuestionCount?testID="+testID;
        request.open("GET", url, true);
        request.onreadystatechange = function (){
            if (request.readyState === XMLHttpRequest.DONE){
                if (request.status === 400){
                    alert("There is not enough question in question bank for you " +
                        "to do this test. Please ask you teacher for more information");
                } else if (request.status === 200){
                    formElement.submit();
                }
            }
        }
        request.send();
    }
}

