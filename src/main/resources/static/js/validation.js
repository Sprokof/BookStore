import {resetClose} from "./reset.js";
import {openResetTwoPopup} from "./confirmReset.js";

let login = document.getElementById('login-btn');
login.addEventListener('click', async () => {
    let user = {
        'login': document.getElementById('login').value,
        'password': await hash(document.getElementById('log-password').value),
    };

    validation(user, "/home/login");
});

let registration = document.getElementById("sign-in-btn");
registration.addEventListener("click", () => {
    let user = {
        'username': document.getElementById('username').value,
        'email': document.getElementById("reg-email").value,
        'password': document.getElementById("reg-password").value,
        'confirmPassword': document.getElementById("confirm-reg-password").value,
    };
    validation(user, "/home/registration");
});



let reset = document.getElementById("continue-btn");
reset.addEventListener("click", () => {
    let resetDto = {
        "login" : localStorage.getItem("user");
        "newPassword" : document.getElementById('new-password').value,
        "confirmResetPassword" : document.getElementById('confirm-reset-password').value,
        "generatedCode" : "0",
        "inputCode" : "0",
    }
    validation(resetDto, "/home/reset")
})

let confirm = document.getElementById("done-btn");
confirm.addEventListener("click", () => {
    let code = document.getElementById("code").value;
    validation(code, "/home/reset/confirm");
})

function validation(obj, url){
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        cache: false,
        dataType: 'json',
        responseType: "json",
        data: JSON.stringify(obj),
        success: function (data) {
            deleteErrorMessages();
            let validationErrors = JSON.parse(JSON.stringify(data));
            let errorMap = new Map(Object.entries(validationErrors));
            let rememberMe = document.querySelector('.remember-me input');
            if (errorMap.size > 0) {
                addErrors(errorMap);
                rememberMe.checked = false;
            } else {
                let value = url.substr(url.lastIndexOf("/") + 1);
                if (value === 'reset') {
                    resetClose();
                    openResetTwoPopup();

                } else {
                    saveUser(obj);
                    if (value === 'login') {
                        let login = obj['login'];
                        if (rememberMe.checked) {
                            rememberUser(login, true)
                        } else {
                            rememberUser(login, false);
                        }
                    }
                    setTimeout(reload, 130);
                }
            }
        }
    })
}


export function deleteErrorMessages(){
    let errorsMessages = document.querySelectorAll(".error-message")
    let elements = document.querySelectorAll('.form-element')
    errorsMessages.forEach((error) => error.classList.remove('active'));
    elements.forEach((element) => element.classList.remove('compression'));

}
async function hash(string) {
    const utf8 = new TextEncoder().encode(string);
    return crypto.subtle.digest('SHA-256', utf8).then(async (hashBuffer) => {
        const hashArray = Array.from(new Uint8Array(hashBuffer));
        return hashArray
            .map((bytes) => bytes.toString(16).padStart(2, '0'))
            .join('')
    });
}


export function clearInputs() {
    let elements = document.querySelectorAll('.form-element input');
    for (let i = 0; i < elements.length; i++) {
        elements[i].value = "";
    }
    deleteErrorMessages();
}


export function logout() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/home/logout',
        cache: false,
        dataType: 'text',
        responseType: "text",
        success: function (code) {
            if (Number(code) === 200) {
                setTimeout(toHome, 100);

            }
        }
    })
}



function reload(){
    window.location.reload();
}

function toHome() {
    window.location.href = "/";
}


function addErrors(errors){
    for(let [field, message] of errors) {
        let error = document.getElementById(field + '-error');
        let parentNode = error.parentNode;
            error.innerText = (String(message));
        error.classList.add('active');
        parentNode.classList.add('compression');
    }
}

function rememberUser(login, flag){
    flag ? localStorage.setItem("remember", 'true') :
       localStorage.setItem("remember", 'false');
}

function extractLogin(obj) {
    let values = [];
    for (let key of Object.keys(obj)) {
        if (obj.hasOwnProperty(key)) {
            values.push(key);
        }
    }
    return obj[values[0]];
}

function saveUser(obj){
    let login = extractLogin(obj);
    localStorage.setItem("user", login);
}







