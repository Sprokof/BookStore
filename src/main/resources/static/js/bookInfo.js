import {addOrRemoveItem, contains, extractISBN, sessionActive, setItemsCount} from "./main.js";
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
    let wishlistDto = {
        "isbn" : extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid']
    }
    if(!sessionActive()){
        openLoginNotice();
    }
    if(!contains(wishlistDto, "/home/wishlist/contains")){
        addOrRemoveItem(wishlistDto, "/home/wishlist/add");
        btn.innerHTML = "Remove from wishlist";
    }
    else {
        addOrRemoveItem(wishlistDto, "/home/wishlist/remove");
        btn.innerHTML = "Add to wishlist";
    }
}

function addOrRemoveFromCart(btn, isbnNode){
    let cartDto = {
        "isbn" : extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid']
    }
    if(!sessionActive()){
        openLoginNotice();
    }
    if(!contains(cartDto, "/home/cart/contains")){
        addOrRemoveItem(cartDto, "/home/cart/add");
        btn.innerHTML = "Remove from cart";
        setItemsCount("+");
    }
    else {
        addOrRemoveItem(cartDto, "/home/cart/remove");
        btn.innerHTML = "Add to cart";
        setItemsCount("-");
    }
}

function controlButtonsTextOnLoad(isbnNode, cartBtn, wishlistBtn){
    let dto = {
        "isbn" : extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid'],
    }
    let cartContains = contains(dto, "/home/cart/contains");
    let wishlistContains = contains(dto, "/home/wishlist/contains");
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
    createAddReviewButton();
    if(reviewExist()){
        let newReviewBtn = document.querySelector('.new-review-btn');
        newReviewBtn.style.opacity = "0.6";
        newReviewBtn.style.pointerEvents = "none";
    }
})


function createAddReviewButton () {
    let reviews = document.querySelector('#book-info .review-container');
    if (reviews.children.length === 0) {
        let addReview = document.createElement('div');
        addReview.classList.add('add-review');
        let i = document.createElement("i");
        i.classList.add('fa', 'fa-plus');
        i.onclick = () => {
            openAddReviewWindow()
        };
        let p = document.createElement('p');
        p.innerText = "add review"
        addReview.appendChild(i);
        addReview.appendChild(p);
        reviews.appendChild(addReview);
    }
}

let reviewsContainer = document.querySelector('.reviews-container');

function openAddReviewWindow () {
    window.scrollTo({ top: 0, behavior: 'smooth' })
    reviewsContainer.classList.add('active');
    blockBackgroundHtml(true);
}


document.addEventListener('mouseup', (e) => {
    if(!reviewsContainer.classList.contains('active')) return ;
    if(!reviewsContainer.contains(e.target)){
        reviewsContainer.classList.remove('active');
        blockBackgroundHtml(false);
    }
})

let newReviewBtn = document.querySelector('#book-info .new-review-btn');
if(newReviewBtn !== null){
    newReviewBtn.onclick = () => {
        openAddReviewWindow();
    }
}

function reviewExist () {
    let bookReviewDto = {
        "isbn" : extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid']
    }
    let result;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/reviews/exist",
        cache: false,
        data: JSON.stringify(bookReviewDto),
        dataType: 'json',
        responseType: 'json',
        async: false,
        success: (exist) => { result = exist; }
    })
    return result;
}