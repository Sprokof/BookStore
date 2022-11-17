import {currentLocation, validateRequest} from "./main.js";

let location = currentLocation();
document.addEventListener('DOMContentLoaded', () => {
    let message = document.querySelector('.result-message span');
    switch (location[2]){
        case ('cart') :
            message.innerText = 'Cart Is Empty';
            message.style.marginLeft = "25px"
            break;
        case ('wishlist') :
            message.innerText = 'Wishlist Is Empty';
            message.style.marginLeft = "10px"
            break;
        case ('registration') :
            message.innerText = 'Confirmed!';
            message.style.marginLeft = "35px";
            break;
        case ("orders") :
            message.innerText = "Orders List Is Empty";
            break;
        default :
            message.innerText = 'No Related Results';
    }
})


let homeBtn = document.querySelector('.result-message button');
homeBtn.onclick = () => { document.location.href = "/"; }

document.onload = () => { validateRequest();}
