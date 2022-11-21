import {validateRequest} from "./main.js";
import {getUser} from "./navbar.js";
import {addErrors, clearInputs, deleteErrorMessages, getMap} from "./validation.js";

document.addEventListener("DOMContentLoaded", () => {
    validateRequest();
    saveCurrentUsername();
});

let currentUsername;

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

let buttons = document.querySelectorAll('.acc-btn');

let sendBtn = buttons[0];
let saveBtn = buttons[1];

if(sendBtn != null) {
    sendBtn.onclick = () => {
        let userDto = {
            'email': document.querySelector('.change-email-container .new-email input').value,
            'sessionid': getUser()['sessionid']
        }
        validateNewEmail(userDto);
    }
}

if(saveBtn != null){
    saveBtn.onclick = () => {
        let userDto = {
            'password': document.querySelector('.password-block #acc-new-password').value,
            'confirmPassword': document.querySelector('.password-block #acc-confirm-new-password').value,
            'sessionid' : getUser()['sessionid']
        }

        validateNewPassword(userDto);
    }
}

function sendVerificationEmail(userDto) {
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

function validateNewPassword(userDto) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/new/password",
        cache: false,
        dataType: 'json',
        responseType: "json",
        data: JSON.stringify(userDto),
        success: function (data) {
            let errorMap = getMap(data);
            if (errorMap.size > 0) {
                addErrors(errorMap);
            }
            else {
                setTimeout(closePasswordWindow, 350);
            }
        }
    })
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
            let errorMap = getMap(data);
            if (errorMap.size > 0) {
                addErrors(errorMap);
            }
            else {
                sendVerificationEmail(userDto);
            }
        }
    })

}
let saveChangesBtn = document.querySelector('.account-container .save-btn');
if(saveChangesBtn != null) {
    saveChangesBtn.onclick = () => {
        let userDto = {
            "username": document.getElementById('acc-username').value,
            "sessionid": getUser()['sessionid']
        }
        validateNewUsername(userDto);
    }

}

function validateNewUsername(userDto){
    if(userDto['username'] === currentUsername) {
        deleteErrorMessages();
        return;
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/new/username",
        cache: false,
        dataType: 'json',
        responseType: "json",
        data: JSON.stringify(userDto),
        success: function (data) {
            let errorMap = getMap(data);
            if (errorMap.size > 0) {
                addErrors(errorMap);
            }
        }
    })
}

function saveCurrentUsername(){
    if(currentUsername == null) {
        currentUsername = document.querySelector('.account-container #acc-username').value;
    }
}



