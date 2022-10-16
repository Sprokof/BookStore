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
    cartBtn.forEach(btn => controlCartContentOnLoad(btn, "books"))
})

