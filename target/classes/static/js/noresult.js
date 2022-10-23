import {currentLocation} from "./main.js";

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
        default :
            message.innerText = 'No Related Results';
    }
})

document.querySelector('.result-message button').onclick = () => {
    document.location.href = "/";
}