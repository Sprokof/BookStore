let resetTwoPopup = document.querySelector('#popup-confirm-reset');
let closeTwoPopup = document.querySelector('#close-confirm-reset');


closeTwoPopup.addEventListener('click', () => {
    closeResetTwoPopup();
    }
)

function openResetTwoPopup(){
    resetTwoPopup.classList.add('down', 'visible');
    blockScroll(resetTwoPopup);
}

function closeResetTwoPopup(){
    resetTwoPopup.classList.add('visible', 'down');
    unblockScroll();

}
