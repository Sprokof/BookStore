import { logout } from "./validation.js"
import {openLoginNotice} from "./notice.js";


$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    })

});

let active = false;
document.addEventListener('DOMContentLoaded', () => {
    invalidateSession();
    autologin();
    let userDto = validateSession();
    if(userDto === null || userDto === undefined) return ;
    if (userDto['inSession']) {
        let menu = document.querySelector('.menu');
        let lastChild = menu.children[4];
        let newChild = createChild(['item', 'main'], ['div', 'a'],
            'Log out');
        newChild.children[0].onclick = () => logout();
        menu.replaceChild(newChild, lastChild);

        if(userDto['admin']){
            newChild = createChild(['item', 'main'], ['div', 'a'],
                'Add book');
            newChild.children[0].onclick = () => addBook();
            menu.appendChild(newChild);
        }
        active = true;
        setCookie(userDto['login']);
        createCartItemLink(userDto);
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
        console.log(text)
        executeQuery(text)
    });


    function executeQuery(value) {
        if(value === null || value === '') return
            value = value.replaceAll(' ', '').toLowerCase();
            document.location.href = '/home/books/search?query=' + value + "&type=popularity";
    }

    let sideMenu = document.getElementById('side-menu');
    sideMenu.addEventListener('click', () => {
        initBooksCategories();
        document.querySelector('.sidebar').classList.toggle('active');
        let location = window.location.pathname.split('/');
        if (location.length === 2 && location[1] === '') {
            document.querySelector('.books-slider').classList.toggle('right');
            document.querySelector('.control-slider').classList.toggle('left');
        }
        if (location[3] === 'add') {
            document.querySelector('.sub-menu').classList.add('none');
        }
        if(location[3] === 'search' || location[2] === 'wishlist'){
            document.querySelector('#card-container').classList.toggle('right');
            let cards = document.querySelectorAll('.card');
            cards.forEach(card => card.classList.toggle('squeeze'));
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
        let user;
        if((user = getUser()) == null) return null;
        let sessionData;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/session/validate",
            cache: false,
            dataType: 'json',
            responseType: 'json',
            data: JSON.stringify(user),
            async: false,
            success: (data) => {
                sessionData = JSON.parse(JSON.stringify(data));
            }
        })
        return sessionData;
    }

    function setCookie(login) {
        document.cookie = "user=" + login;

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
       document.location = '/home/book/add';
   }


   function autologin() {
       let user = getUser();
       if(user === null) return ;
       if (loaded() && user['remember'] === 'true') {
           navigator.sendBeacon('/autologin', user['login']);
       }
   }

   function invalidateSession() {
       let user = getUser();
       if(user === null || !loaded()) return ;
       navigator.sendBeacon("/invalidate", user['login']);
   }


   export function getUser(){
       if(localStorage.getItem('user') === null) return null;
           return JSON.parse(localStorage.getItem('user'));
   }


   export function createCartItemLink(userDto){
       let cart = document.querySelector('#cart-link');
       $.ajax({
           type: "POST",
           contentType: "application/json",
           url: "/home/cart/quantity",
           data: JSON.stringify(userDto),
           cache: false,
           dataType: 'json',
           responseType: 'json',
           success: function (dto) {
               let cartDto = JSON.parse(JSON.stringify(dto));
               cart.innerText = "Cart (" + cartDto['itemsQuantity'] + ")";
           }
       });
   }

   export function sessionValid(){
        return active && (getUser() != null);
   }

   function clearSearchValue(){
        document.querySelector('#search-input').value = '';
   }

   let wishlist = document.getElementById('wishlist');
   wishlist.addEventListener("click", () => {
       if(!sessionValid()){
           openLoginNotice();
       }
       else {
           document.location.href = "/home/wishlist?login=" + (getUser()['login'].toLowerCase());
       }
   })