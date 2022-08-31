let loginPopup = document.querySelector('#popup-login');
let openLogin = document.querySelector('#open-login');
let closeLogin = document.querySelector('#close-login');

openLogin.addEventListener('click', (e) => {
    signInClose();
    closeResetTwoPopup();
    resetClose();
    loginOpen();

});

closeLogin.addEventListener('click', (e) => {
    loginClose();

});

function loginClose(){
    loginPopup.classList.remove('visible', 'down');
    unblockScroll();

}

function loginOpen(){
    loginPopup.classList.add('down', 'visible');
    blockScroll(loginPopup);

}


