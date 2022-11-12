import { loginClose } from './login.js';
import {blockBackgroundHtml} from "./notice.js";
import {openAcceptWindow} from "./window.js";


let singInPopup = document.querySelector('#popup-sign-up');
let openSignIn = document.querySelector('#sign-up');
let closeSignIn = document.querySelector('#close-sign-up');

export function signUpClose(){
    singInPopup.classList.remove('visible', 'down');
    blockBackgroundHtml(false);

}

export function signUpOpen(){
    singInPopup.classList.add('down', 'visible');
}

openSignIn.addEventListener("click", () => {
    loginClose();
    signUpOpen();
});

closeSignIn.addEventListener('click', () => {
    signUpClose();
});

export function registrationSuccess(){
    openAcceptWindow();
}