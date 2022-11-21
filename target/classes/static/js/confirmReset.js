import {blockBackgroundHtml} from "./notice.js";
import {closeSuccessWindow, openSuccessWindow} from "./window.js";
import {getSavedLogin} from "./validation.js";

let resetTwoPopup = document.querySelector('#popup-confirm-reset');
let closeTwoPopup = document.querySelector('#close-confirm-reset');


closeTwoPopup.addEventListener('click', () => {
    closeResetTwoPopup();
    })

export function openResetTwoPopup(){
    resetTwoPopup.classList.add('down', 'visible');
    blockBackgroundHtml(true);
}

export function closeResetTwoPopup(){
    resetTwoPopup.classList.remove('visible', 'down');
    blockBackgroundHtml(false);
}

let resendCode = document.getElementById("resend-code");
resendCode.addEventListener("click", () => {
    sendNewCode();
})

function setActive(){
    resendCode.classList.remove('disable');
}


export function resetSuccess(){
    openSuccessWindow();
    setTimeout(closeSuccessWindow, 2200);
    setTimeout(closeResetTwoPopup, 2700);
}

function sendNewCode () {
    let resetPasswordDto = {
        'login' : getSavedLogin()
    }
    console.log(getSavedLogin());
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/resend/code",
        data: JSON.stringify(resetPasswordDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: () => {
            resendCode.classList.add('disable');
            setTimeout(setActive, 60000);
        }
    })
}
