import {validateRequest} from "./main.js";
import {getUser} from "./navbar";

document.addEventListener("DOMContentLoaded", () => {
    validateRequest();
});


let emailContainer = document.querySelector('.change-email-container');
let changeEmail = document.querySelectorAll('.account-container .change-link')[0];
changeEmail.onclick = () => {
    emailContainer.classList.add('open');
    blockAccountHtml(true);
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
sendBtn.onclick = () => {
    let newEmail = document.querySelector('.change-email-container .new-email input').value;
    sendVerificationEmail(newEmail);
}

function sendVerificationEmail(email) {
      let userDto = {
          "email" : email,
          "sessionid" : getUser()['sessionid']
      }
      $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/new/email",
        data: JSON.stringify(userDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: () => {
            setTimeout(closeEmailWindow, 130);
    }
});
}

function closeEmailWindow() {
    emailContainer.classList.remove('open');
    blockAccountHtml(false);
}