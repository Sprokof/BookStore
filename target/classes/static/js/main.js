import {openLoginNotice} from "./notice.js";
import {getUser} from "./navbar.js";
import {userEmail} from "./validation.js";


export function controlWishlistContent(btn, pageName) {
    btn.onclick = () => {
        if (!sessionActive()) {
            openLoginNotice();
        } else {
            let wishlistDto = requestDto(btn);
            if (!contains(wishlistDto, "/wishlist/item/contains")) {
                itemAdd(wishlistDto, "/wishlist/item/add");
                if(pageName === 'wishlist') {
                    setTimeout(reload, 120);
                }
                else {
                    fullHeart(btn, true);
                }
            } else {
                itemRemove(wishlistDto, "/wishlist/item/remove");
                if(pageName === 'wishlist') {
                    setTimeout(reload, 120);
                }
                else {
                    fullHeart(btn, false);
                }

            }
        }
    }
}

export function requestDto(btn){
    let lasIndex = (btn.parentNode.parentNode.children[1].children.length - 1);
    let isbnNode = btn.parentNode.parentNode.children[1].children[lasIndex];
    return {
        "isbn": extractISBN(isbnNode),
        "sessionid" : getUser()['sessionid'],
    };
}


export function contains(dto, url) {
    let responseDto
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: url + "?isbn=" + dto['isbn'],
        headers: {"session" : dto['sessionid']},
        cache: false,
        responseType: 'json',
        async: false,
        success: (data) => {
            responseDto = JSON.parse(JSON.stringify(data));
        }
    })
    return responseDto['itemContains'];
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
        if (contains(wishlistDto, "/wishlist/item/contains")) {
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
            if (contains(cartDto, "/cart/item/contains")) {
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
            if (!contains(cartDto, "/cart/item/contains")) {
                itemAdd(cartDto, "/cart/item/add")
                pageName === "home" ?
                    changeCartBtnText(btn, "Remove From Cart") : inCart(btn, true);
                symbol = "+";
            } else {
                itemRemove(cartDto, "/cart/item/remove")
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

export function reload(){
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

export function itemRemove(dto, url){
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        data: JSON.stringify(dto),
        url: url,
        cache: false,
        dataType: 'json',
        responseType: "json",
    })
}

export function itemAdd(dto, url){
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

export function userAccept(){
    let login = userEmail();
    if(login === null) return null;
    let accept;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/bookstore/accept/user?=" + login,
        cache: false,
        dataType: 'json',
        async: false,
        success : (result) => {
            accept = result;
        }
    })
    return accept;
}

export function deleteUser() {
    localStorage.removeItem('user');
}


let infos = document.querySelectorAll('.book-info');
    for (let i = 0; i < infos.length; i++) {
        let title = infos[i].children[0];
        title.onclick = () => {
            let lastIndex = (infos[i].children.length - 1);
            let isbn = extractISBN(infos[i].children[lastIndex]);
            document.location.href = '/book?isbn=' + isbn;
        }
    }

export function extractISBN(node){
    let length = (node.innerText.length - 6);
    return (node.innerText.substr(6, length))
}

export function validateRequest() {
    if(!validateConditions()) return ;
    let user = getUser();
    if(user !== null) {
        let userDto = getUserData(user);
        let href = document.location.href;
        let index = 0;
        if ((index = href.indexOf("=")) >= 0) {
            let inputLogin = href.substr(index + 1);
            let currentUsername = userDto['username'];
            let currentEmail = userDto['email'];
            if (currentUsername === inputLogin || currentEmail === inputLogin) return;
            badRequest()
        }
    }
    else {
        badRequest();
    }

}

let badRequest = () => { document.location.href = '/error'; }

function getUserData(user){
    let userDto ;
    let sessionid = user['sessionid']
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/validate/user/request",
        headers: {"session" : sessionid},
        cache: false,
        dataType: 'json',
        async: false,
        success : (dto) => {
            userDto = dto;
        }
    })
    return userDto;
}

    function validateConditions(){
        let location = currentLocation()[1];
        return location === 'cart' || location === 'wishlist'
            || location === 'account' || location === 'orders';
    }
