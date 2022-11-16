import {loginOpen} from "./login.js";

let noticeMessage = document.querySelector('#log-in-notice');
let loginBtn = document.querySelector('#log-in-notice button');
let closeBtn = document.querySelector('#log-in-notice .close-notice-message-btn');

export function openLoginNotice() {
    noticeMessage.classList.add('show');
}

loginBtn.addEventListener("click", () => {
    noticeMessage.classList.remove('show');
    loginOpen();
    blockBackgroundHtml(true);
})

closeBtn.onclick = () => {
    noticeMessage.classList.remove('show');
}


export function blockBackgroundHtml(flag){
    let navbar = document.querySelector('#navbar');
    let sidebar = document.querySelector('#sidebar');
    let slider = document.querySelector('.books-slider');
    let cards = document.querySelector('#card-container');
    let cart = document.querySelector('.cart-container');
    let checkout = document.querySelector('.checkout-container');
    let confirm = document.querySelector('#popup-confirm-reset');
    let success = document.querySelectorAll('#success-window');
    let accept = document.querySelector('#accept-window');
    let registration = document.querySelector('#popup-sign-in');
    let bookInfo = document.querySelector('#book-info');
    if(flag) {
        navbar.style.pointerEvents = "none";
        if(slider != null) {
            slider.style.pointerEvents = "none";
        }
        if(cards != null) {
            cards.style.pointerEvents = "none";
        }

        if(cart != null) {
            cart.style.pointerEvents = "none";
        }

        if(checkout != null && success[1].classList.contains('open')) {
            checkout.style.pointerEvents = "none";
        }

        if(confirm != null && success[0].classList.contains('open')) {
            confirm.style.pointerEvents = "none";
        }

        if(registration != null && accept.classList.contains('open')) {
            registration.style.pointerEvents = "none";
            document.querySelector('.close-window-btn').style.pointerEvents = "auto";
        }

        if(bookInfo != null){
            bookInfo.style.pointerEvents = "none";
        }

        sidebar.style.pointerEvents = "none";

    }
    else {
        navbar.style.pointerEvents = "auto";

        if (slider != null) {
            slider.style.pointerEvents = "auto";
        }
        if (cards != null) {
            cards.style.pointerEvents = "auto";
        }
        if (cart != null) {
            cart.style.pointerEvents = "auto";
        }

        if(checkout != null) {
            checkout.style.pointerEvents = "auto";
        }

        if(confirm != null) {
            confirm.style.pointerEvents = "auto";
        }

        if(registration != null && !accept.classList.contains('open')) {
            registration.style.pointerEvents = "auto";
        }

        if(bookInfo != null){
            bookInfo.style.pointerEvents = "auto";
        }

        sidebar.style.pointerEvents = "auto";
    }



}