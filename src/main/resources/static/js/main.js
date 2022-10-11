import {getUser, sessionValid} from "./navbar.js";
import {openLoginNotice} from "./notice.js";

export function controlWishlistContent(btn, pageName) {
    btn.onclick = () => {
        if (!sessionValid()) {
            openLoginNotice();
        } else {
            let data = newRequestData(btn);
            let isbn = data['isbn'];
            if (!contains(data, "/home/wishlist/contains")) {
                navigator.sendBeacon('/home/wishlist/add', isbn);
                pageName === 'wishlist' ? removeCard(isbn) : fullHeart(btn, true);
            } else {
                navigator.sendBeacon('/home/wishlist/remove', isbn);
                pageName === 'wishlist' ? removeCard(isbn) : fullHeart(btn, false)
            }
        }
    }
}

export function newRequestData(btn){
    let lasIndex = (btn.parentNode.parentNode.children[1].children.length - 1);
    let isbn = btn.parentNode.parentNode.children[1].children[lasIndex];
    let login = getUser()['login'];
    return {
        "login": login,
        "isbn": isbn.innerText,
    };
}


export function contains(requestDto, url) {
    let dto;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        cache: false,
        dataType: 'json',
        responseType: 'json',
        data: JSON.stringify(requestDto),
        async: false,
        success: (data) => {
            dto = JSON.parse(JSON.stringify(data));
        }
    })
    return dto['itemContains'];
}

export function fullHeart(btn, flag) {
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

export function setItemsCount(symbol){
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


export function controlWishlistContentOnLoad(btn){
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

export function controlCartContentOnLoad(btn, pageName){
        if (!sessionValid()) {
            if(pageName === "home") {
                changeCartBtnText(btn, "Add To Cart");
            }
        } else {
            let data = newRequestData(btn);
            if (contains(data, "/home/cart/contains")) {
                pageName === "home" ?
                    changeCartBtnText(btn, "Remove From Cart") : inCart(btn, true);
            } else {
                pageName === "home" ?
                    changeCartBtnText(btn, "Add To Cart") : inCart(btn, false);
            }
        }
}

function changeCartBtnText(btn, text){
    if(text.length > 11) {
        btn.innerHTML = '<font size = 3>' + text + '</font>';
    }
    else {
        btn.innerHTML = text;
    }
}

export function controlCartContent(btn, pageName) {
    btn.onclick = () => {
        let symbol;
        if (!sessionValid()) {
            openLoginNotice();
        } else {
            let data = newRequestData(btn);
            let isbn = data['isbn'];
            if (!contains(data, "/home/cart/contains")) {
                navigator.sendBeacon("/home/cart/add", isbn);
                pageName === "home" ?
                    changeCartBtnText(btn, "Remove From Cart") : inCart(btn, true);
                symbol = "+";
            } else {
                navigator.sendBeacon("/home/cart/remove", isbn);
                pageName === "home" ?
                    changeCartBtnText(btn, "Add To Cart") : inCart(btn, false);
                symbol = "-";
            }
            setItemsCount(symbol);
        }
    }
}

function inCart(btn, flag){
    let cart = btn;
    if (flag) {
        cart.classList.add("select")
    }
    else {
        if(cart.classList.contains("select")){
            cart.classList.remove("select");
        }
    }
}

function removeCard(isbn){
    let card = document.getElementById(isbn);
    card.remove();
}
