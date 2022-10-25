import { loginClose } from './login.js';
import {blockBackgroundHtml} from "./notice.js";
import {openAcceptWindow} from "./window.js";


let singInPopup = document.querySelector('#popup-sign-in');
let openSignIn = document.querySelector('#sign-in');
let closeSignIn = document.querySelector('#close-sign-in');

export function signInClose(){
    singInPopup.classList.remove('visible', 'down');
    blockBackgroundHtml(false);

}

export function signInOpen(){
    singInPopup.classList.add('down', 'visible');
}

openSignIn.addEventListener("click", () => {
    loginClose();
    signInOpen();
});

closeSignIn.addEventListener('click', () => {
    signInClose();
});

export function registrationSuccess(){
    openAcceptWindow();
}