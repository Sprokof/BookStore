import {blockBackgroundHtml} from "./notice.js";

let resetTwoPopup = document.querySelector('#popup-confirm-reset');
let closeTwoPopup = document.querySelector('#close-confirm-reset');


closeTwoPopup.addEventListener('click', () => {
    closeResetTwoPopup();
    }
)

export function openResetTwoPopup(){
    resetTwoPopup.classList.add('down', 'visible');
}

export function closeResetTwoPopup(){
    resetTwoPopup.classList.remove('visible', 'down');
    blockBackgroundHtml(false);
}

let resendCode = document.getElementById("resend-code");
resendCode.addEventListener("click", () => {
    navigator.sendBeacon("/home/resend/code");
    resendCode.classList.add('disable');
    setTimeout(setActive, 60000);
})

function setActive(){
    resendCode.classList.remove('disable');
}