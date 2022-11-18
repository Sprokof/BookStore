import {validateRequest} from "./main.js";
import {getUser} from "./navbar.js";
import {validation} from "./validation.js";

document.addEventListener("DOMContentLoaded", () => {
    validateRequest();
});


let emailContainer = document.querySelector('.change-email-container');
let changeEmail = document.querySelectorAll('.account-container .change-link')[0];
if(changeEmail != null) {
    changeEmail.onclick = () => {
        emailContainer.classList.add('open');
        blockAccountHtml(true);
    }
}

document.addEventListener('mouseup', (e) => {
    if(!emailContainer.classList.contains('open')) return ;
    if(!emailContainer.contains(e.target)){
        closeEmailWindow();
    }
})

function blockAccountHtml(flag){
    let accountContainer = document.querySelector('.account-container');
    let value = "auto";
    if(flag) value = "none"
    accountContainer.style.pointerEvents = value;
    emailContainer.style.pointerEvents = "auto";
}

let sendBtn = document.querySelector('.send-btn');
if(sendBtn != null) {
    sendBtn.onclick = () => {
        let userDto = {
            'email': document.querySelector('.change-email-container .new-email input').value,
            'sessionid': getUser()['sessionid'],
        }
        validation(userDto, "/account/send/new/email");
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
    blockAccountHtml(false);
}