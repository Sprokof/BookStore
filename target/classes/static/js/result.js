import {currentLocation, validateRequest} from "./main.js";

let location = currentLocation();
document.addEventListener('DOMContentLoaded', () => {
    switch (location[2]){
        case ('cart') :
            messageToCenter("Cart Is Empty", "25px")
            break;
        case ('wishlist') :
            messageToCenter("Wishlist Is Empty", "10px");
            break;
        case ('registration' || "newemail") :
            messageToCenter("Confirmed!", "35px");
            clearSavedEmail();
            break;
        case ("orders") :
            messageToCenter("Orders List Is Empty", "0px")
            break;
        default :
            messageToCenter("No Related Results", "0px");
    }
})


let homeBtn = document.querySelector('.result-message button');
homeBtn.onclick = () => { document.location.href = "/"; }

document.onload = () => { validateRequest();}

function clearSavedEmail(){
    localStorage.removeItem('email');
}

function messageToCenter(text, marginValue){
    let message = document.querySelector('.result-message span');
    message.innerText = text;
    message.style.marginLeft = marginValue;
}