import {itemAdd, itemRemove, contains, extractISBN, sessionActive, setItemsCount} from "./main.js";
import {getUser} from "./navbar.js";
import {blockBackgroundHtml, openLoginNotice} from "./notice.js";

let buttons = document.querySelectorAll('#book-info .item-container .buttons button');
let isbnNode = document.querySelector('#book-info .isbn');

let wishlistBtn = buttons[0];
wishlistBtn.onclick = () => {
    addOrRemoveFromWishlist(wishlistBtn, isbnNode);
}

let cartBtn = buttons[1];
cartBtn.onclick = () => {
    addOrRemoveFromCart(cartBtn, isbnNode);
}

function addOrRemoveFromWishlist(btn, isbnNode){
    if(!sessionActive()){ openLoginNotice(); return; }
    let wishlistDto = {
        "isbn" : extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid']
    }

    if(!contains(wishlistDto, "/wishlist/item/contains")){
        itemAdd(wishlistDto, "/wishlist/item/add");
        btn.innerHTML = "Remove from wishlist";
    }
    else {
        itemRemove(wishlistDto, "/wishlist/item/remove");
        btn.innerHTML = "Add to wishlist";
    }
}

function addOrRemoveFromCart(btn, isbnNode){
    if(!sessionActive()){ openLoginNotice(); return; }
    let cartDto = {
        "isbn" : extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid']
    }

    if(!contains(cartDto, "/cart/item/contains")){
        itemAdd(cartDto, "/cart/item/add");
        btn.innerHTML = "Remove from cart";
        setItemsCount("+");
    }
    else {
        itemRemove(cartDto, "/cart/item/remove");
        btn.innerHTML = "Add to cart";
        setItemsCount("-");
    }
}

function controlButtonsTextOnLoad(isbnNode, cartBtn, wishlistBtn){
    if(!sessionActive()){
        cartBtn.innerHTML = "Add to cart";
        wishlistBtn.innerHTML = "Add to wishlist";
        return;
    }
    let dto = {
        "isbn" : extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid'],
    }
    let cartContains = contains(dto, "/cart/item/contains");
    let wishlistContains = contains(dto, "/wishlist/item/contains");
    if(!cartContains){
        cartBtn.innerHTML = "Add to cart";
    }
    else {
        cartBtn.innerHTML = "Remove from cart";
    }

    if(!wishlistContains){
        wishlistBtn.innerHTML = "Add to wishlist";
    }
    else {
        wishlistBtn.innerHTML = "Remove from wishlist";
    }
}

document.addEventListener('DOMContentLoaded', () => {
    controlButtonsTextOnLoad(isbnNode, cartBtn, wishlistBtn);
    if(!sessionActive()){
        let review = document.querySelector('#book-info .fa.fa-plus').parentNode;
        invisibleNode(review);
        return;
    }
    if(reviewExist()){
        let newReviewBtn = document.querySelector('.new-review-btn');
        invisibleNode(newReviewBtn);
    }
})


let reviewContainer = document.querySelector('.review-container');

function openAddReviewWindow () {
    window.scrollTo({ top: 0, behavior: 'smooth' })
    reviewContainer.classList.add('active');
    blockBackgroundHtml(true);
}


document.addEventListener('mouseup', (e) => {
    if(!reviewContainer.classList.contains('active')) return ;
    if(!reviewContainer.contains(e.target)){
        reviewContainer.classList.remove('active');
        blockBackgroundHtml(false);
    }
})

let reviewBtn = document.querySelector('#book-info .fa.fa-plus');
if(reviewBtn !== null){
    reviewBtn.onclick = () => {
        openAddReviewWindow();
    }
}

function reviewExist () {
    let result;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/reviews/exist?isbn=" + extractISBN(isbnNode) + "&sessionid=" + getUser()['sessionid'],
        cache: false,
        dataType: 'json',
        responseType: 'json',
        async: false,
        success: (exist) => { result = exist; }
    })
    return result;
}

function invisibleNode(btn){
    btn.style.opacity = "0.6";
    btn.style.pointerEvents = "none";
}
