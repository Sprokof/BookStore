import {blockBackgroundHtml} from "./notice.js";

let successWindows = document.querySelectorAll('.success-window');

export function openSuccessWindow(){
    successWindows.forEach(window => window.classList.add('open'));
    blockBackgroundHtml(true)
}

export function closeSuccessWindow(){
    blockBackgroundHtml(false);
    successWindows.forEach(window => window.classList.add('close'));
    successWindows.forEach(window => window.classList.remove('open'));
}

