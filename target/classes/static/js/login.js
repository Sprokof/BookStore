import { signInClose } from "./registration.js";
import { resetClose } from "./reset.js";
import { closeResetTwoPopup  } from "./confirmReset.js";
import { clearInputs } from "./validation.js";
import { blockBackgroundHtml } from "./notice.js";



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
    blockBackgroundHtml(false)

}

export function loginOpen(){
    clearInputs();
    signInClose();
    resetClose();
    closeResetTwoPopup();
    loginPopup.classList.add('down', 'visible');
    blockBackgroundHtml(true)

}



