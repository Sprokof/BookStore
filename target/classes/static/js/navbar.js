import {logout, userEmail} from "./validation.js"
import {openLoginNotice} from "./notice.js";
import {currentLocation, deleteUser, sessionActive, userAccept} from "./main.js";


$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    })

});

document.addEventListener('DOMContentLoaded', () => {
    loadInit();
    let session = validateSession();
    if(session == null) return ;
        if(session['active']) {
            let menu = document.querySelector('.menu');
            let lastChild = menu.children[4];
            let newChild = createChild(['item', 'main'], ['div', 'a'],
                'Log out');
            newChild.children[0].onclick = () => logout();
            menu.replaceChild(newChild, lastChild);

            if (session['adminSession']) {
                newChild = createChild(['item', 'main'], ['div', 'a'],
                    'Add book');
                newChild.children[0].onclick = () => addBook();
                menu.appendChild(newChild);
            }
            createCartItemLink();
        }
    clearSearchValue();
})


function initBooksCategories(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/categories",
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: function (data) {
            let categories = JSON.parse(JSON.stringify(data))
            addOrUpdate(categories);
            executeCategorySearch();
        }

    });

}

    function addOrUpdate(categories) {
        let newNode;
        let currentNode;
        let childrenNodes = document.querySelector('.sub-menu').children;
        let subMenu = document.querySelector('.sub-menu');
        if (childrenNodes.length < categories.length) {
            for (let i = childrenNodes.length; i < categories.length; i++) {
                newNode = document.createElement('a');
                newNode.classList.add('sub-item')
                newNode.innerText = categories[i]['category'];
                subMenu.appendChild(newNode);
            }
        } else {
            for (let i = 0; i < categories.length; i++) {
                newNode = document.createElement('a');
                newNode.innerText = categories[i]['category'];
                currentNode = childrenNodes.item(i);
                subMenu.replaceChild(newNode, currentNode);
            }
        }
    }


    document.getElementById('search').addEventListener('click', (e) => {
        let text = document.getElementById('search-input').value;
        executeQuery(text)
    });


    function executeQuery(value) {
        if(value === null || value === '') return
            value = value.replaceAll(' ', '');
            document.location.href = '/books/search?query=' + value + "&type=relevance&page=1";

    }

    let sideMenu = document.getElementById('side-menu');
    sideMenu.addEventListener('click', () => {
        initBooksCategories();
        document.querySelector('.sidebar').classList.toggle('active');
        let location = currentLocation();
        if (location.length === 2 && location[1] === '') {
            document.querySelector('.books-slider').classList.toggle('right');
            document.querySelector('.control-slider').classList.toggle('left');
        }
        if (location[1] === 'session') {
            document.querySelector('.sub-menu').classList.add('none');
        }
        if(location[2] === 'search' || location[1] === 'wishlist'){
            let card = document.querySelector('#card-container');
            if(card !== null)
            card.classList.toggle('right');
            let cards = document.querySelectorAll('.card');
            cards.forEach(card => card.classList.toggle('squeeze'));
        }
        if(location[1] === 'cart'){
            let cart = document.querySelector('.cart-container');
            if(cart !== null) cart.classList.toggle('compression');
        }
        if(location[1] === 'orders'){
            let orders = document.querySelector('.order-container');
            orders.style.left = "25%";
        }

        if(location[1] === 'book'){
            let sidebar = document.querySelector('#sidebar');
            sidebar.classList.add('reveal');
        }
        document.querySelector(".container-fluid").classList.toggle('compression');
        document.querySelector("#menu").classList.toggle('compression');

    })

function executeCategorySearch() {
    let items = document.querySelectorAll('.sub-item');
    for (let item of items) {
        item.onclick = () => {
            let category = item.innerText;
            executeQuery(category);
        }
    }
}
export function validateSession() {
    createCookie();
    let user = getUser();
    if(user == null) return null;
        let sessionDto
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/session/validate",
            cache: false,
            dataType: 'json',
            data: JSON.stringify(user),
            responseType: 'json',
            async: false,
            success: (data) => {
                sessionDto = JSON.parse(JSON.stringify(data));
            }
        })

        return sessionDto;
    }

    function createCookie(){
        document.cookie = "cookie=" + String ((Math.random() * 10));
    }

    function loaded(){
        return document.cookie === '';
    }

    export function createChild(classes, elements, innerText){
        let div = document.createElement(elements[0]);
        let a = document.createElement(elements[1]);
        div.classList.add(classes[0]);
        a.classList.add(classes[1]);
        a.innerText = innerText;
        div.appendChild(a);
        return div;
    }

   export function addBook() {
       let user = getUser();
       document.location = '/session?id=' + user['sessionid'];
   }



   function invalidateSession() {
       if(!loaded() || getUser() === null) return;
       let sessionDto = {
           "sessionid" : getUser()['sessionid']
       }
       $.ajax({
           type: "DELETE",
           contentType: "application/json",
           url: "/invalidate",
           data: JSON.stringify(sessionDto),
           cache: false,
           dataType: 'json',
           responseType: "json",
           async: false,
           success : () => { deleteUser(); }
       })
   }

   function autologin() {
        if(!autologinCondition()) return ;
        let user = getRememberedUser();
        $.ajax({
           type: "POST",
           contentType: "application/json",
           url: "/autologin",
           dataType: 'json',
           data: JSON.stringify(user),
           cache: false,
           async: false
        })
   }


   export function getUser(){
        return JSON.parse(localStorage.getItem('user'));
   }


   export function createCartItemLink(){
       let cart = document.querySelector('#cart-link');
       let sessionid = getUser()['sessionid'];
       $.ajax({
           type: "GET",
           contentType: "application/json",
           url: "/cart/item/quantity",
           headers: {"session": sessionid},
           cache: false,
           dataType: 'json',
           responseType: 'json',
           success: function (dto) {
               let cartDto = JSON.parse(JSON.stringify(dto));
               cart.innerText = "Cart (" + cartDto['itemsQuantity'] + ")";
           }
       });
   }


   function clearSearchValue(){
        document.querySelector('#search-input').value = '';
   }

    let wishlist = document.getElementById('wishlist');
    wishlist.onclick = () => { toPage("/wishlist"); }

    let cart = document.getElementById('cart-link');
    cart.onclick = () => { toPage("/cart"); }

    let orders = document.getElementById("orders");
    orders.onclick = () => { toPage("/orders"); }

    let account = document.getElementById('account-link');
    account.onclick = () => { toPage("/account"); }


export function createAcceptNotice(){
        let windowOpen = document.querySelector('#accept-window').classList.contains('open');
        let location = currentLocation()[2];
        let accept = userAccept();
        if(accept === null || windowOpen ||
            location === 'registration' || getUser() != null) return;
        if(!accept){
            let div = document.createElement('div');
            div.classList.add('accept-message');
            let p = document.createElement('p');
            let a = document.createElement('a');
            a.onclick = () => { resendLink(); }
            a.innerText = "(click to send again)";
            p.innerText = "Go to link in " + userEmail() + " to finish registration ";
            p.appendChild(a);
            div.appendChild(p);
            let containerFluid = document.querySelector('.container-fluid');
            let navbar = containerFluid.children[0];
            containerFluid.insertBefore(div, navbar);
        }
}

    function resendLink() {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/registration/resend",
            data: JSON.stringify(getUser()),
            cache: false,
            dataType: 'json',
        });
    }

    function getRememberedUser(){
        return JSON.parse(localStorage.getItem('rememberedUser'));
    }

    function toPage(url){
        if(!sessionActive()) openLoginNotice() ;
        document.location.href = url + "?user=" + getUser()['login'];
    }

    function autologinCondition() {
        return loaded() && getRememberedUser() != null;
    }

    function loadInit(){
        invalidateSession();
        autologin();
        createAcceptNotice();
    }
