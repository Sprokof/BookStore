let resetTwoPopup = document.querySelector('#popup-confirm-reset');
let closeTwoPopup = document.querySelector('#close-confirm-reset');


closeTwoPopup.addEventListener('click', () => {
    closeResetTwoPopup();
    }
)

function openResetTwoPopup(){
    resetTwoPopup.classList.add('down', 'visible');
}

export function closeResetTwoPopup(){
    resetTwoPopup.classList.remove('visible', 'down');
}
