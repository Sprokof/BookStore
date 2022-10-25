import {blockBackgroundHtml} from "./notice.js";
import {createAcceptNotice} from "./navbar.js";
import {signInClose} from "./registration.js";

let successWindows = document.querySelectorAll('#success-window');

export function openSuccessWindow(){
    successWindows.forEach(window => window.classList.add('open'));
    blockBackgroundHtml(true)
}

export function closeSuccessWindow(){
    blockBackgroundHtml(false);
    successWindows.forEach(window => window.classList.add('close'));
    successWindows.forEach(window => window.classList.remove('open'));
}

let acceptWindow = document.querySelector('#accept-window');
let closeAcceptWindow = document.querySelector('#accept-window .close-window-btn');

closeAcceptWindow.onclick = () => {
    acceptWindow.classList.add('close');
    acceptWindow.classList.remove('open');
    setTimeout(signInClose, 700);
    setTimeout(createAcceptNotice, 1000);
    blockBackgroundHtml(false);
}

export function openAcceptWindow(){
    acceptWindow.classList.add('open');
    blockBackgroundHtml(true);
}

