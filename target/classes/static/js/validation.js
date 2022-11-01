import {resetClose} from "./reset.js";
import {openResetTwoPopup, resetSuccess} from "./confirmReset.js";
import {getUser} from "./navbar.js";
import {registrationSuccess} from "./registration.js";
import {deleteUser} from "./main.js";
import {executePurchase} from "./checkout.js";

let login = document.getElementById('login-btn');
login.addEventListener('click', async () => {
    let password = document.getElementById('log-password').value;
    let login = document.getElementById('login').value;
    let user = {
        'login': login,
        'password': await hash(password),
        'sessionid' : await generateSessionId(),
    };
    validation(user, "/home/login");
});

let registration = document.getElementById("sign-in-btn");
registration.addEventListener("click", async () => {
    let password = document.getElementById("reg-password").value;
    let email = document.getElementById('reg-email').value;
    let user = {
        'username': document.getElementById('username').value,
        'email': email,
        'password': password,
        'confirmPassword': document.getElementById("confirm-reg-password").value,

    };
    validation(user, "/home/registration");
});



let reset = document.getElementById("continue-btn");
reset.addEventListener("click", () => {
    let confirmDto = {
        "login" : getUser()['login'],
        "newPassword" : document.getElementById('new-password').value,
        "confirmResetPassword" : document.getElementById('confirm-reset-password').value,
    }
    validation(confirmDto, "/home/reset");
})

let confirm = document.getElementById("done-btn");
confirm.addEventListener("click", () => {
    let confirmDto = {
        "login" : getUser()['login'],
        "inputCode" :document.getElementById("code").value,
    }
    validation(confirmDto, "/home/reset/confirm");
})

export function validation(obj, url){
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
            let remember = document.querySelector('.remember-me input').checked;
            if (errorMap.size > 0) {
                addErrors(errorMap);
            } else {
                let value = url.substr(url.lastIndexOf("/") + 1);
                if (value === 'reset') {
                    resetClose();
                    openResetTwoPopup();
                }
                else if(value === 'confirm'){
                    resetSuccess();
                }

                else if(value === 'checkout'){
                    executePurchase();
                }

                else {
                    if(value === 'registration'){
                        saveEmail(obj['email']);
                        registrationSuccess();
                    }
                    else if (value === 'login') {
                        saveUser(obj);
                        rememberUser(remember);
                        setTimeout(reload, 130);
                    }

                }
            }
        }
    })
}


export function deleteErrorMessages(){
    let errorsMessages = document.querySelectorAll(".error-message");
    let formElements = document.querySelectorAll('.form-element');
    let addElements = document.querySelectorAll('.add-element');
    errorsMessages.forEach((error) => error.classList.remove('active'));
    formElements.forEach((element) => element.classList.remove('compression'));
    addElements.forEach((element) => element.classList.remove('compression'));
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
    let user = getUser();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/home/logout',
        data : JSON.stringify(user),
        cache: false,
        dataType: 'text',
        responseType: "text",
        success: function (status) {
            let code = JSON.parse(JSON.stringify(status));
            if (Number(code) === 200) {
                deleteUser();
                setTimeout(backHome, 100);

            }
        }
    })
}



function reload(){
    window.location.reload();
}

function backHome() {
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


function saveUser(obj){
    let user = {
        "login" : obj['login'],
        "sessionid": obj['sessionid'],
    }
    updateUser(user)
    localStorage.removeItem('email');
}

function rememberUser(flag){
    if(flag) {
        localStorage.setItem('rememberedUser', JSON.stringify(getUser()));
    }
    else {
        localStorage.removeItem('rememberedUser');
    }

}

export function updateUser(user){
    localStorage.setItem("user", JSON.stringify(user));
}

async function generateSessionId(){
    return (String (([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4)
                .toString(16))));
}

function saveEmail(email){
    localStorage.setItem('email', email);
}

export function userEmail(){
    return localStorage.getItem('email');
}
