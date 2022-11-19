import {validateRequest} from "./main.js";
import {getUser} from "./navbar.js";
import {addErrors, clearInputs, deleteErrorMessages} from "./validation.js";

document.addEventListener("DOMContentLoaded", () => {
    validateRequest();
});


let emailContainer = document.querySelector('.change-email-container');
let passwordContainer = document.querySelector('.change-password-container');

let changeLinks = document.querySelectorAll('.account-container .change-link');

let changeEmail = changeLinks[0];
let changePassword = changeLinks[1];


if(changeEmail !== null) {
    changeEmail.onclick = () => {
        emailContainer.classList.add('open');
        blockAccountHtml(true);
    }
}

if(changePassword !== null){
    changePassword.onclick = () => {
        passwordContainer.classList.add('open');
        blockAccountHtml(true);
    }
}

document.addEventListener('mouseup', (e) => {
    if(emailContainer.classList.contains('open')) {
        if (!emailContainer.contains(e.target)) {
            closeEmailWindow();
        }
    }

    if(passwordContainer.classList.contains('open')) {
        if (!passwordContainer.contains(e.target)) {
            closePasswordWindow();
        }
    }
})


function blockAccountHtml(flag){
    let accountContainer = document.querySelector('.account-container');
    let value = "auto";
    if(flag) value = "none"
    accountContainer.style.pointerEvents = value;
    emailContainer.style.pointerEvents = "auto";
    passwordContainer.style.pointerEvents = "auto";
}

let buttons = document.querySelectorAll('.btn');

let sendBtn = buttons[0];
let saveBtn = buttons[1];

if(sendBtn != null) {
    sendBtn.onclick = () => {
        let userDto = {
            'email': document.querySelector('.change-email-container .new-email input').value,
            'sessionid': getUser()['sessionid'],
        }
        validateNewEmail(userDto);
    }
}

export function sendVerificationEmail(userDto) {
      $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/send/new/email",
        data: JSON.stringify(userDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: () => {
            setTimeout(closeEmailWindow, 350);
    }
});
}

function closeEmailWindow() {
    emailContainer.classList.remove('open');
    clearInputs();
    blockAccountHtml(false);
}

function closePasswordWindow () {
    passwordContainer.classList.remove('open');
    clearInputs();
    blockAccountHtml(false);
}

function validateNewEmail(userDto) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/send/new/email",
        cache: false,
        dataType: 'json',
        responseType: "json",
        data: JSON.stringify(userDto),
        success: function (data) {
            deleteErrorMessages();
            let validationErrors = JSON.parse(JSON.stringify(data));
            let errorMap = new Map(Object.entries(validationErrors));
            if (errorMap.size > 0) {
                addErrors(errorMap);
            }
            else {
                sendVerificationEmail(userDto);
            }
        }
    })
}

