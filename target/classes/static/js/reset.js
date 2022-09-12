import { loginClose } from './login.js'
let resetPopup = document.querySelector('#popup-reset');
let closeReset = document.querySelector('#close-reset');
let openReset = document.querySelector('#reset');

openReset.addEventListener("click", () => {
    loginClose();
    resetOpen();
})

closeReset.addEventListener('click', (e) => {
    resetClose();
})

function resetOpen(){
    resetPopup.classList.add('down', 'visible');
}

export function resetClose(){
    resetPopup.classList.remove('visible', 'down');
}

