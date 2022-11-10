import {logout, userEmail} from "./validation.js"
import {openLoginNotice} from "./notice.js";
import {currentLocation, sessionActive, userAccept} from "./main.js";


$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    })

});

document.addEventListener('DOMContentLoaded', () => {
    manageSession();
    createAcceptNotice();
    let session = validateSession();
    if(session === undefined) return ;
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
            document.location.href = '/home/books/search?query=' + value + "&type=popularity";
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
        if (location[3] === 'add') {
            document.querySelector('.sub-menu').classList.add('none');
        }
        if(location[3] === 'search' || location[2] === 'wishlist'){
            let card = document.querySelector('#card-container');
            if(card !== null)
            card.classList.toggle('right');
            let cards = document.querySelectorAll('.card');
            cards.forEach(card => card.classList.toggle('squeeze'));
        }
        if(location[2] === 'cart'){
            let cart = document.querySelector('.cart-container');
            if(cart !== null) cart.classList.toggle('compression');
        }
        if(location[2] === 'orders'){
            let orders = document.querySelector('.order-container');
            orders.style.left = "25%";
        }

        if(location[2] === 'book'){
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
        if(user == null) return undefined;
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
       document.location = '/home/book/add?user=' + user['login'] + '&sessionid=' + getUser()['sessionid'];
   }



   function manageSession() {
       if(!loaded() || getRememberedUser() === null) return;
       let sessionDto = {
           "sessionid" : getRememberedUser()['sessionid']
       }
       $.ajax({
           type: "POST",
           contentType: "application/json",
           url: "/invalidate",
           data: JSON.stringify(sessionDto),
           cache: false,
           dataType: 'json',
           responseType: "json",
           async: false,
           success : () => {
               autologin();
           }
       })
   }

   function autologin() {
       $.ajax({
           type: "POST",
           contentType: "application/json",
           url: "/autologin",
           data: JSON.stringify(getRememberedUser()),
           cache: false,
           dataType: 'json',
           responseType: "json",
           async: false
       })
   }


   export function getUser(){
        return JSON.parse(localStorage.getItem('user'));
   }


   export function createCartItemLink(){
       let cart = document.querySelector('#cart-link');
       let user = getUser();
       $.ajax({
           type: "POST",
           contentType: "application/json",
           url: "/home/cart/quantity",
           data: JSON.stringify(user),
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
   wishlist.addEventListener("click", () => {
       if(!sessionActive()){
           openLoginNotice();
       }
       else {
           document.location.href = "/home/wishlist?user=" + (getUser()['login']);
       }
   })

    let cart = document.getElementById("cart-link");
    cart.addEventListener('click', () => {
        if(!sessionActive()){
            openLoginNotice();
        }
        else {
            document.location.href = "/home/cart?user=" + (getUser()['login']);
        }
   })

    let orders = document.getElementById("orders");
    orders.addEventListener('click', () => {
        if(!sessionActive()){
            openLoginNotice();
        }
        else {
            document.location.href = "/home/orders?user=" + (getUser()['login']);
        }
    })


export function createAcceptNotice(){
        let windowOpen = document.querySelector('#accept-window').classList.contains('open');
        let location = currentLocation()[2];
        let accept = userAccept();
        if(accept === undefined || windowOpen || location === 'registration') return;
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
            url: "bookstore/registration/resend",
            data: JSON.stringify(getUser()),
            cache: false,
            dataType: 'json',
        });
    }

    function getRememberedUser(){
        return JSON.parse(localStorage.getItem('rememberedUser'));
    }