function addRadioEvent() {
    let radios = document.querySelectorAll("input[type='radio']");
    for (let i = 0; i < radios.length; i++) {
        let radio = radios[i];
        radio.onclick = onChangeQuestion;
    }
}

function onChangeQuestion() {
    console.log(this.name + '-' + this.value);
    sessionStorage.setItem(this.name, this.value);
}

function addSubmitEvent(quizID) {
    let submitButton = document.getElementById("submit");
    submitButton.addEventListener("click", function () {
        submitAnswer(quizID);
    })
}

function getSubmission() {
    let keys = Object.keys(sessionStorage);
    let choices = [];
    for (let i = 0; i < keys.length; i++) {
        let choice = {};
        choice['questionID'] = keys[i];
        choice['choiceID'] = sessionStorage.getItem(keys[i]);
        choices.push(choice);
    }
    return choices;
}

function submitAnswer(quizID) {
    let choices = getSubmission();
    let url = "SubmitQuiz?quizID=" + quizID;
    let request = new XMLHttpRequest();
    request.open("POST", url, true);
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                window.location = "ShowResultAction?quizID=" + quizID;
            }
        }

    }
    request.send(JSON.stringify(choices));
}

function countdown(endTime) {
    let countDownDate = endTime;
    let x = setInterval(function () {
        var now = new Date().getTime();
        var distance = countDownDate - now;
        // Time calculations for days, hours, minutes and seconds
        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Display the result in the element with id="demo"
        document.getElementById("countdown").innerHTML = minutes + "m " + seconds + "s ";

        // If the count down is finished, write some text
        if (distance < 0) {
            document.getElementById("countdown").innerHTML = "Time out";
            clearInterval(x);
            submitAnswer('${requestScope.QUIZ.ID}')
        }
    }, 1000);
}

function updateSubmission(quizID) {
    let x = setInterval(function () {
        let choices = getSubmission();
        let request = new XMLHttpRequest();
        let url = "UpdateSubmissionAction?quizID=" + quizID;
        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                console.log(request.status);
                if (request.status === 400) {
                    clearInterval(x);
                    submitAnswer(quizID);
                }
            }
        }
        request.open("POST", url, true);
        request.send(JSON.stringify(choices));
    }, 5 * 1000);
}