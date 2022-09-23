import {blockLoginPage, loginClose} from './login.js'
let resetPopup = document.querySelector('#popup-reset');
let closeReset = document.querySelector('#close-reset');
let openReset = document.querySelector('#reset');
let notice = document.querySelector('.notice-message');


openReset.addEventListener("click", () => {
    if(!findUser()){
        blockLoginPage(true)
        notice.classList.add('show');
        return;
    }
    loginClose();
    resetOpen();
})

closeReset.addEventListener('click', (e) => {
    resetClose();
})

function resetOpen(){
    if(!findUser()){
        blockLoginPage(true)
        notice.classList.add('show');
        return;
    }
    resetPopup.classList.add('down', 'visible');
}

export function resetClose(){
    resetPopup.classList.remove('visible', 'down');
}

function findUser(){
    return localStorage.getItem("user") != null;
}

