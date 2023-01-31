import {itemAdd, itemRemove, contains, extractISBN, sessionActive, setItemsCount} from "./main.js";
import {getUser} from "./navbar.js";
import {blockBackgroundHtml, openLoginNotice} from "./notice.js";

let buttons = document.querySelectorAll('#book-info .item-container .buttons button');
let isbnNode = document.querySelector('#book-info .isbn');

let rowReviews = document.querySelectorAll('#book-info .row-review');

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

    hideReviews();

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
    let bookReviewDto;
    let isbn = extractISBN(isbnNode);
    let sessionid = getUser()['sessionid'];
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/reviews/exist?isbn=" + isbn,
        headers: {'session' : sessionid},
        cache: false,
        dataType: 'json',
        responseType: 'json',
        async: false,
        success: (dto) => {
            bookReviewDto = JSON.parse(JSON.stringify(dto)); }
    })
    return findReview(bookReviewDto['author'])
}

function invisibleNode(btn) {
    btn.style.opacity = "0.6";
    btn.style.pointerEvents = "none";
}

let loadBtn = document.querySelector("#book-info .load-btn");
if(loadBtn != null) {
    let index = 5;
    loadBtn.onclick = () => {
        revealReviews(index);
        index += 5;
    }
}


function hideReviews(){
    if(rowReviews.length < 6) return ;
    for(let i = 5; i < rowReviews.length; i ++ ){
        rowReviews[i].classList.add("hide");
    }
}

function revealReviews(value) {
    value = correctingIndex(value)
    let revealSize = 5;
    for (let i = 0; i < revealSize; i++) {
        let index = (i + value);
        if (index >= rowReviews.length) {
            loadBtn.classList.add("disable");
            return;
        }
        rowReviews[index].classList.add('reveal');
    }
}

function correctingIndex(index){
        if(index >= rowReviews.length){
            index = (rowReviews.length - 1)
        }
        return index;
}

function findReview(username){
        if(username == null) return false
        for(let row of rowReviews) {
            let review = row.children[0];
            let authorInfo = review.children[0].innerText;
            let index = (authorInfo.indexOf(":") + 2);
            let substrLength = (authorInfo.length - index);
            let author = authorInfo.substr(index, substrLength);
            if (author === username) {
                review.children[0].innerText = "Author: You";
                review.children[0].style.color = "slategray";
            }
        }
        return true;
}


let noticeBtn = document.querySelector('#book-info .notice-btn');
if(noticeBtn != null) {
    noticeBtn.onclick = () => {
        noticeBtn.classList.add('create');
    }
}



