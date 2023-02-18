import {loginClose} from './login.js'
import {blockBackgroundHtml} from "./notice.js";

let resetPopup = document.querySelector('#popup-reset');
let closeReset = document.querySelector('#close-reset');
let openReset = document.querySelector('#reset');
let notice = document.querySelector('.notice-message');


openReset.addEventListener("click", () => {
    loginClose();
    notice.classList.remove('show');
    resetOpen();
})

closeReset.addEventListener('click', (e) => {
    resetClose();
})

function resetOpen() {
    resetPopup.classList.add('down', 'visible');

}

export function resetClose(){
    resetPopup.classList.remove('visible', 'down');
}


