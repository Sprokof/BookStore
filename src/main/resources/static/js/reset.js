import {blockLoginPage, loginClose} from './login.js'
let resetPopup = document.querySelector('#popup-reset');
let closeReset = document.querySelector('#close-reset');
let openReset = document.querySelector('#reset');
let notice = document.querySelector('.notice-message');


openReset.addEventListener("click", () => {
    loginClose();
    if(!findUser()){
        notice.classList.add('show');
        return;
    }
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

function findUser(){
    return localStorage.getItem("user") != null;
}

