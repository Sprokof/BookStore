let singInPopup = document.querySelector('#popup-sign-in');
let openSignIn = document.querySelector('#sign-in');
let closeSignIn = document.querySelector('#close-sign-in');

function signInClose(){
    singInPopup.classList.remove('visible', 'down');
    unblockScroll();
}

function signInOpen(){
    singInPopup.classList.add('down', 'visible');
    blockScroll(singInPopup);
}

openSignIn.addEventListener("click", () => {
    loginClose();
    signInOpen();
});

closeSignIn.addEventListener('click', () => {
    signInClose();
});