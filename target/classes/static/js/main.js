import {openLoginNotice} from "./notice.js";
import {getUser} from "./navbar.js";

export function controlWishlistContent(btn, pageName) {
    btn.onclick = () => {
        if (!sessionActive()) {
            openLoginNotice();
        } else {
            let wishlistDto = requestDto(btn);
            if (!contains(wishlistDto, "/home/wishlist/contains")) {
                addOrRemoveItem(wishlistDto, "/home/wishlist/add");
                pageName === 'wishlist' ? setTimeout(reload, 120) : fullHeart(btn, true);
            } else {
                addOrRemoveItem(wishlistDto, "/home/wishlist/remove");
                pageName === 'wishlist' ? setTimeout(reload, 120) : fullHeart(btn, false);
            }
        }
    }
}

export function requestDto(btn){
    let lasIndex = (btn.parentNode.parentNode.children[1].children.length - 1);
    let isbn = btn.parentNode.parentNode.children[1].children[lasIndex];
    return {
        "sessionid" : getUser()['sessionid'],
        "isbn": isbn.innerText,
    };
}


export function contains(bookDto, url) {
    let dto;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        cache: false,
        dataType: 'json',
        responseType: 'json',
        data: JSON.stringify(bookDto),
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
    if(!sessionActive()){
        fullHeart(btn, false);
    }
    else {
        let wishlistDto = requestDto(btn);
        if (contains(wishlistDto, "/home/wishlist/contains")) {
            fullHeart(btn, true);
        } else {
            fullHeart(btn, false);
        }

    }
}

export function controlCartContentOnLoad(btn, pageName){
        if (!sessionActive()) {
            pageName === "home" ?
                changeCartBtnText(btn, "Add To Cart") : inCart(btn, false);

        } else {
            let cartDto = requestDto(btn);
            if (contains(cartDto, "/home/cart/contains")) {
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
        if (!sessionActive()) {
            openLoginNotice();
        } else {
            let cartDto = requestDto(btn);
            if (!contains(cartDto, "/home/cart/contains")) {
                addOrRemoveItem(cartDto, "/home/cart/add")
                pageName === "home" ?
                    changeCartBtnText(btn, "Remove From Cart") : inCart(btn, true);
                symbol = "+";
            } else {
                addOrRemoveItem(cartDto, "/home/cart/remove")
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

function reload(){
    window.location.reload();
}

export function sessionActive () {
    let sessionDto;
    let user = getUser();
    if(user == null) return false;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/session/active",
        cache: false,
        data: JSON.stringify(user),
        dataType: 'json',
        responseType: 'json',
        async: false,
        success: (data) => {
            sessionDto = JSON.parse(JSON.stringify(data));
        }
    })
    return sessionDto['active'];
}

function addOrRemoveItem(dto, url){
    $.ajax({
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(dto),
        url: url,
        cache: false,
        dataType: 'json',
        responseType: "json",
    })
}

export function currentLocation(){
    return window.location.pathname.split('/');

}

