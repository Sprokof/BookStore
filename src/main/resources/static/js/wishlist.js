import {
    controlCartContent,
    controlCartContentOnLoad, controlWishlistContent,
    newRequestData
} from "./main.js";


let wishlistBtn = document.querySelectorAll('.wishlist.btn');
let cartBtn = document.querySelectorAll('.shop.cart.btn');
wishlistBtn.forEach(btn => controlWishlistContent(btn, "wishlist"));
cartBtn.forEach(btn => controlCartContent(btn, "wishlist"))

document.addEventListener("DOMContentLoaded", () => {
    cartBtn.forEach(btn => controlCartContentOnLoad(btn, "wishlist"));

})