import {getUser, sessionValid} from "./navbar.js";
import {openLoginNotice} from "./notice.js";


let sliderControlBtn = document.querySelectorAll('.control-slider span');
let books = document.querySelectorAll('.card');
let book_page = Math.ceil(books.length/4);
let left = 0;
let sliderStep = 25.20;
let maxMove = (sliderStep * (books.length - 4));

let rightMover = () => {
    left = (left + sliderStep);
    if (book_page === 1) {
        left = 0;
    }
    for (const item of books) {
        if (left > maxMove) {
            left = (left - sliderStep);
        }
        item.style.left = '-' + left + '%';
        if(left === maxMove){
            return;
        }
    }
}

let leftMover = () => {
    left = (left - sliderStep);
    if(left <= 0){
        left = 0;
    }
    for (const item of books) {
        if (book_page > 1) {
            item.style.left = '-' + left + '%';
        }
    }
}

sliderControlBtn[1].onclick = () => {
    rightMover();
}

sliderControlBtn[0].onclick = () => {
    leftMover();
}


let infos = document.querySelectorAll('.book-info');
for(let i = 0; i < infos.length; i ++){
    let title = infos[i].children[0];
    title.onclick = () => {
        let isbn = infos[i].children[3];
        document.location.href = '/home/book?isbn=' + isbn.innerText;
    }
}



let wishListBtn = document.querySelectorAll('.wishlist.btn');
for(let btn of wishListBtn) {
    controlWishlistContent(btn)
}

let cartBtn = document.querySelectorAll('.cart.btn');
for(let btn of cartBtn){
    controlCartContent(btn);
}

    function contains(map, url) {
        let dto;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: url,
            cache: false,
            dataType: 'json',
            responseType: 'json',
            data: JSON.stringify(map),
            async: false,
            success: (data) => {
                dto = JSON.parse(JSON.stringify(data));
            }
        })
        return dto['itemContains'];
    }


    function fullHeart(btn, flag) {
    let heart = btn.children[0];
        if (flag) {
            if(heart.classList.contains('far')){
                heart.classList.remove('far');
                heart.classList.remove('fa-heart');
            }
            btn.children[0].classList.add('fa-solid', 'fa-heart')
        } else {
            if(heart.classList.contains('fa-solid')){
                heart.classList.remove('fa-solid');
                heart.classList.remove('fa-heart');
            }
            btn.children[0].classList.add('far', 'fa-heart')
        }
    }

document.addEventListener('DOMContentLoaded', () => {
    for(let btn of wishListBtn){
        if(!sessionValid()){
            fullHeart(btn, false);
        }
        else {
            let data = newRequestData(btn);
            if (contains(data, "/home/wishlist/contains")) {
                fullHeart(btn, true);
            } else {
                fullHeart(btn, false);
            }

        }

    }

    for(let btn of cartBtn){
        if(!sessionValid()){
            changeCartBtnText(btn, "Add To Cart")
        }
        else {
            let data = newRequestData(btn);
            if(contains(data, "/home/cart/contains")){
                changeCartBtnText(btn, "Remove From Cart");
            }
            else {
                changeCartBtnText(btn, "Add To Cart");
            }
        }
    }
})

function newRequestData(btn){
    let isbn = btn.parentNode.parentNode.children[1].children[3];
    let login = getUser()['login'];
    return {
        "login": login,
        "isbn": isbn.innerText,
    };
}

function changeCartBtnText(btn, text){
    if(text.length > 11) {
        btn.innerHTML = '<font size = 3>' + text + '</font>';
    }
    else {
        btn.innerHTML = text;
    }
}

function setItemsCount(symbol){
    let cart = document.getElementById('cart-link');
    let startIndex = (cart.innerText.indexOf("(") + 1);
    let endIndex = (cart.innerText.indexOf(")"));
    let length = (endIndex - startIndex)
    let currentValue = cart.innerText.substr(startIndex, length);
    if(currentValue === '0' && symbol === '-') return ;
    let newValue;
    if(symbol === "+"){
        newValue = (Number(currentValue) + 1);
    }

    else if(symbol === "-"){
        newValue = (Number(currentValue) - 1);
    }
    cart.innerText = "Cart (" + newValue + ")"

}

function controlWishlistContent(btn) {
    btn.onclick = () => {
        if (!sessionValid()) {
            openLoginNotice();
        } else {
            let data = newRequestData(btn);
            let isbn = data['isbn'];
            if (!contains(data, "/home/wishlist/contains")) {
                navigator.sendBeacon('/home/wishlist/add', isbn);
                fullHeart(btn, true);
            } else {
                navigator.sendBeacon('/home/wishlist/remove', isbn);
                fullHeart(btn, false)
            }
        }
    }
}

function controlCartContent(btn) {
    btn.onclick = () => {
        let symbol;
        if (!sessionValid()) {
            openLoginNotice();
        } else {
            let data = newRequestData(btn);
            let isbn = data['isbn'];
            if (!contains(data, "/home/cart/contains")) {
                navigator.sendBeacon("/home/cart/add", isbn);
                changeCartBtnText(btn, "Remove From Cart");
                symbol = "+";
            } else {
                navigator.sendBeacon("/home/cart/remove", isbn);
                changeCartBtnText(btn, "Add To Cart");
                symbol = "-";
            }
            setItemsCount(symbol);
        }
    }
}

