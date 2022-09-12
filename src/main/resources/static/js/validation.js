import {closeResetTwoPopup} from "./confirmReset.js";

let login = document.getElementById('login-btn');
login.addEventListener('click', async () => {
    let user = {
        'login': document.getElementById('login').value,
        'password': await hash(document.getElementById('log-password').value),
        'remembered': document.querySelector('.remember-me input').checked,
        'ipAddress': '0'
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
        'ipAddress': '',
        'remembered': false
    };
    validation(user, "/home/registration");
});



let reset = document.getElementById("continue-btn");
reset.addEventListener("click", () => {
    deleteErrorMessages();
    let newPassword = document.getElementById("new-password").value;
    let confirmPassword = document.getElementById("confirm-reset-password").value;
    if(newPassword !== confirmPassword){
        let confirmElement = document.getElementById("confirm-reset-password").parentNode;
        let error = document.createElement("p");
        error.classList.add("error-message");
        error.innerText = "Passwords not equals";
        confirmElement.appendChild(error);
    }
    else {
        let hashPassword = hash(newPassword);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            cache: false,
            url: '/home/reset',
            dataType: 'text',
            data: JSON.stringify(hashPassword),
            success: function () {
                resetClose();
                openResetTwoPopup();
            }
        })

    }
})

let confirm = document.getElementById("done-btn");
confirm.addEventListener("click", () => {
    let code = document.getElementById("code").value;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/reset/confirm",
        cache: false,
        dataType: 'text',
        responseType: "json",
        data: JSON.stringify(code),
        success: function (data) {
            deleteErrorMessages();
            let correct = JSON.parse(JSON.stringify(data));
            if(!correct){
                let error = document.createElement('p');
                error.classList.add('error-message');
                error.innerText = "Wrong confirmation code";
                document.getElementById("code").parentNode.appendChild(error);
            }
            else {
                closeResetTwoPopup();
            }
        }
    })
})

function validation(user, url){
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        cache: false,
        dataType: 'json',
        responseType: "json",
        data: JSON.stringify(user),
        success: function (data) {
            deleteErrorMessages();
            let validationErrors = JSON.parse(JSON.stringify(data));
            let errorMap = new Map(Object.entries(validationErrors));
            if (errorMap.size > 0) {
                addErrors(errorMap);
                document.querySelector('.remember-me input').checked = false;
            } else {
                setTimeout(reload, 130);
            }
        }
    })
}


export function deleteErrorMessages(){
    let errorsMessages = document.querySelectorAll(".error-message")
    let errorsSymbols = document.querySelectorAll('.error-symbol.symbol-active');
    let labels = document.querySelectorAll('.form-element label');
    let formElements = document.querySelectorAll('.form-element');
    errorsMessages.forEach(message => message.classList.remove('active'));
    errorsSymbols.forEach(symbol => symbol.classList.remove('symbol-active'));
    labels.forEach(label => label.style.marginLeft = '-5px');
    formElements.forEach(element => element.classList.remove('compression'));

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

    function closeAnotherMessages(id, parent) {
        let messages = document.querySelectorAll('.error-message.active');
        for (let message of messages) {
            if (message.parentNode.children[1].id === id) {
                continue;
            }
            message.classList.remove('active');
            parent.classList.remove('compression');
        }

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
                setTimeout(reload, 100);
            }
        }
    })
}
function reload(){
    window.location.reload();
}

function addErrors(errors){
    for(let [field, message] of errors) {
        let wrongInput = document.getElementById(field);
        let error = document.getElementById(field + '-error')
        error.innerText = (String(message));
        let parentNode = wrongInput.parentNode;
        let errorSymbol = parentNode.children[0].children[0];
        let label = parentNode.children[0];
        activateErrorSymbol(errorSymbol, parentNode, error);
        label.style.marginLeft = '5px';
    }
}

function activateErrorSymbol(errorSymbol, parentNode, message){
    errorSymbol.classList.add('symbol-active');
    errorSymbol.addEventListener('click', () => {
        closeAnotherMessages(parentNode.children[1].id, parentNode)
        parentNode.classList.toggle('compression')
        message.classList.toggle('active');
    })
}


