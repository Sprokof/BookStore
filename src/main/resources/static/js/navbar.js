import {loginOpen} from "./login.js";
import { logout } from "./validation.js"

$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    })

});


document.addEventListener('DOMContentLoaded', () => {
    invalidateSession();
    autologin();
    let sessionDto = validateSession();
    if(sessionDto === null || sessionDto === undefined) return ;
    if (sessionDto['activeSession']) {
        let menu = document.querySelector('.menu');
        let lastChild = menu.children[4];
        let newChild = createChild(['item', 'main'], ['div', 'a'],
            'Log out');
        newChild.children[0].onclick = () => logout();
        menu.replaceChild(newChild, lastChild);

        if(sessionDto['userAdmin']){
            newChild = createChild(['item', 'main'], ['div', 'a'],
                'Add book');
            newChild.children[0].onclick = () => addBook();
            menu.appendChild(newChild);
        }
        setCookie(sessionDto['userLogin']);
    }
})

    document.querySelector("#side-menu").addEventListener("click", (e) => {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/categories",
            cache: false,
            dataType: 'json',
            responseType: 'json',
            success: function (data) {
                let categories = JSON.parse(JSON.stringify(data))
                addOrUpdate(categories)
            }

        });
    })

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


    let categories = document.querySelectorAll('.sub-item');
    for (let i = 0; i < categories.length; i++) {
        categories[i].addEventListener('click', (e) => {
            let category = categories[i].innerText.toLowerCase();
            executeQuery(category)
        });
    }

    document.getElementById('search').addEventListener('click', (e) => {
        let text = document.getElementById('search-input').value;
        executeQuery(text)
    });


    function executeQuery(value) {
        if (value !== null) {
            value = value.replaceAll('\s', '').toLowerCase();
            document.location.href = '/home/books/search?query=' + value + "&type=popularity";
        }
    }

    let sideMenu = document.getElementById('side-menu');
    sideMenu.addEventListener('click', () => {
        document.querySelector('.sidebar').classList.toggle('active');
        if (window.location.pathname.split('/').length === 2) {
            document.querySelector('.books-slider').classList.toggle('right');
            document.querySelector('.control-slider').classList.toggle('left');
        }
        if (window.location.pathname.split('/')[3] === 'add') {
            document.querySelector('.sub-menu').classList.add('none');
        }
        document.querySelector(".container-fluid").classList.toggle('compression');
        document.querySelector("#menu").classList.toggle('compression');

    })

    document.addEventListener('DOMContentLoaded', () => {
        let menu = document.querySelector('.menu');
        let lastChild = menu.lastChild;
        if (lastChild.innerText === 'Log in') {
            for (let i = 1; i < menu.children.length - 1; i++) {
                menu.children.onclick = () => {
                    let ok = confirm('Login?');
                    if (ok) {
                        loginOpen();
                    }
                }
            }
        }
    })

function validateSession() {
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
       if(user === null) return ;
       if (!loaded()) return;
       navigator.sendBeacon("/invalidate", user['login']);
   }


   function getUser(){
        if(localStorage.getItem('user') === null) return null;
           return JSON.parse(localStorage.getItem('user'));
   }
