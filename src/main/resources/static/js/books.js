import {
    controlCartContent,
    controlCartContentOnLoad,
    controlWishlistContent,
    controlWishlistContentOnLoad
} from "./main.js";


let wishlistBtn = document.querySelectorAll('.wishlist.btn');
let cartBtn = document.querySelectorAll('.shop.cart.btn');
    wishlistBtn.forEach(btn => controlWishlistContent(btn, "books"));
    cartBtn.forEach(btn => controlCartContent(btn, "books"))

document.addEventListener("DOMContentLoaded", () => {
    wishlistBtn.forEach(btn => controlWishlistContentOnLoad(btn));
    cartBtn.forEach(btn => controlCartContentOnLoad(btn, "books"));
    findNotAvailableBooks();
})


function findNotAvailableBooks () {
    let statuses = document.querySelectorAll('.status')
    for(let status of statuses){
        let statusText = status.innerText;
        if(statusText === 'Not available'){
            let card = status.parentNode.parentNode;
            let cartBtn = card.children[2].children[1];
            cartBtn.style.opacity = '0.6';
            cartBtn.style.pointerEvents = 'none';
        }
    }
}

