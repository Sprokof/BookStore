import {signInClose, signInOpen} from "./registration.js";
import { resetClose } from "./reset.js";
import { closeResetTwoPopup  } from "./confirmReset.js";
import { clearInputs } from "./validation.js";


let loginPopup = document.querySelector('#popup-login');
let openLogin = document.querySelector('#open-login');
let closeLogin = document.querySelector('#close-login');

openLogin.addEventListener('click', (e) => {
    loginOpen();

});

closeLogin.addEventListener('click', (e) => {
    loginClose();

});

export function loginClose(){
    loginPopup.classList.remove('visible', 'down');

}

export function loginOpen(){
    clearInputs();
    signInClose();
    resetClose();
    closeResetTwoPopup();
    loginPopup.classList.add('down', 'visible');
}

export function blockLoginPage(flag){
    let value;
    flag ? value = "none" : value = "auto";
    console.log(value)
    loginPopup.style.pointerEvents = value;
}
let noticeMessage = document.querySelector('.notice-message');

let noticeButton = document.querySelector('.notice-message button');
noticeButton.addEventListener('click', () => {
    noticeMessage.classList.remove('show');
    signInOpen();
});

let closeNotice = document.querySelector('.close-notice-message-btn');
closeNotice.addEventListener('click', () => {
    noticeMessage.classList.remove('show');
})


