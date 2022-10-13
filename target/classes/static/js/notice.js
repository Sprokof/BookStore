import {signInOpen} from "./registration.js";
import {loginOpen} from "./login.js";


let noticeButton = document.querySelector('#sign-in-notice button');
let noticeMessage = document.querySelector('#sign-in-notice');

noticeButton.addEventListener('click', () => {
    noticeMessage.classList.remove('show');
    signInOpen();
});

let closeNoticeBtn = document.querySelectorAll('.close-notice-message-btn');
for(let btn of closeNoticeBtn) {
    btn.addEventListener('click', () => {
        noticeMessage.classList.remove('show');
        blockBackgroundHtml(false);
    })
}

export function openLoginNotice(){
    noticeMessage = document.querySelector('#log-in-notice');
    noticeMessage.classList.add('show');
    noticeButton = document.querySelector('#log-in-notice button');

    noticeButton.addEventListener("click", () => {
        noticeMessage.classList.remove('show');
        loginOpen();
    })
    blockBackgroundHtml(true);

}

export function blockBackgroundHtml(flag){
    let navbar = document.querySelector('#navbar');
    let sidebar = document.querySelector('#sidebar');
    let slider = document.querySelector('.books-slider');
    let cards = document.querySelector('#card-container');
    if(flag) {
        navbar.style.pointerEvents = "none";
        if(slider != null) {
            slider.style.pointerEvents = "none";
        }
        if(cards != null) {
            cards.style.pointerEvents = "none";
        }
            sidebar.style.pointerEvents = "none";
    }
    else {
        navbar.style.pointerEvents = "auto";
        if(slider != null) {
            slider.style.pointerEvents = "auto";
        }
        if(cards != null) {
            cards.style.pointerEvents = "auto";
        }
        sidebar.style.pointerEvents = "auto";
    }
}