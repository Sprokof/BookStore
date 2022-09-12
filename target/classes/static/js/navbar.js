import {loginOpen} from "./login.js";
import { logout } from "./validation.js"

$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    })

});


document.addEventListener('DOMContentLoaded', () => {
    autologin();
    let user = userDto();
    if (user['inSession']) {
        let menu = document.querySelector('.menu');
        let lastChild = menu.children[4];
        let newChild = createChild(['item', 'main'], ['div', 'a'],
            'Log out');
        newChild.children[0].onclick = () => logout();
        menu.replaceChild(newChild, lastChild);

        if(Boolean(user['isAdmin'])){
            newChild = createChild(['item', 'main'], ['div', 'a'],
                'Add book');
            newChild.children[0].onclick = () => addBook();
            menu.appendChild(newChild);
        }
    }
})

    document.querySelector("#side-menu").addEventListener("click", (e) => {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/instance/popular/categories",
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
        if (childrenNodes.length < categories.length) {
            for (let i = childrenNodes.length; i < categories.length; i++) {
                newNode = document.createElement('a');
                newNode.innerText = categories[i]['category'];
                document.querySelector('.sub-menu').appendChild(newNode);
            }
        } else {
            for (let i = 0; i < categories.length; i++) {
                newNode = document.createElement('a');
                newNode.innerText = categories[i]['category'];
                currentNode = childrenNodes.item(i);
                document.querySelector('.sub-menu').replaceChild(newNode, currentNode);
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
        console.log("in")
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
        if (window.location.pathname.split('/').pop() === 'home') {
            document.querySelector('.books-slider').classList.toggle('right');
            document.querySelector('.control-slider').classList.toggle('left');
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

    export function userDto() {
        let user;
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/user/data",
            cache: false,
            dataType: 'json',
            responseType: 'json',
            async: false,
            success: (data) => {
                user = JSON.parse(JSON.stringify(data));
            }
        })
        return user;
    }

    function createCookie() {
        document.cookie = 'status=reload'
    }

    function autologin() {
        if(!loaded()) return ;
        navigator.sendBeacon("/autologin");
        createCookie();

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

   export function addBook(){
        document.location = '/home/book/add';
    }

