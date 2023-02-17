import {logout, updateUser, userEmail} from "./validation.js"
import {blockBackgroundHtml, openLoginNotice} from "./notice.js";
import {currentLocation, deleteUser, sessionActive, userAccept} from "./main.js";
let bell = document.querySelector('.bell');
let noticeContainer = document.querySelector('.notice-container');



$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    });

});

document.addEventListener('DOMContentLoaded', () => {
    loadInit();
    let session = validateSession();
    if(session === null) return ;
        if(session['active']) {
            let menu = document.querySelector('.menu');
            let lastChild = menu.children[4];
            let newChild = createChild(['item', 'main'], ['div', 'a'],
                'Log out');
            newChild.children[0].onclick = () => logout();
            menu.replaceChild(newChild, lastChild);

            if (session['adminSession']) { createAdmins(menu); }
            createCartItemLink();
            setInterval(updateNoticesCount, 300);

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
                newNode.classList.add('sub-item', 'category')
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

    });

function executeCategorySearch() {
    let items = document.querySelectorAll('.category');
    for (let item of items) {
        item.onclick = () => {
            let category = item.innerText;
            executeQuery(category);
        };
    }
}

export function validateSession() {
    createCookie();
    let user = getUser();
    if(user === null) return null;
        let sessionDto;
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
        });

        return sessionDto;
    }

    function createCookie(){
        for(let i = 0; i < 10; i ++) {
            let number = String(Math.round(Math.random() * 10));
            document.cookie = "cookie=" + number;
        }
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
       document.location = '/add/session?id=' + user['sessionid'];
   }

   function updateBook() {
        let user = getUser();
        document.location = '/update/session?id=' + user['sessionid'];
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
           async: false,
           success: () => {
               updateUser(user);
           }
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


   function clearSearchValue() {
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
        let location = currentLocation()[1];
        let accept = userAccept();
        if(accept === null || windowOpen || location === 'registration' || userEmail() === null) return;
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
        let user = {
            "login" : userEmail()
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/registration/resend",
            data: JSON.stringify(user),
            cache: false,
            dataType: 'json',
        });
    }

    function getRememberedUser(){
        return JSON.parse(localStorage.getItem('rememberedUser'));
    }

    function toPage(url){
        if(!sessionActive()){ openLoginNotice(); return; }
        document.location.href = url + "?user=" + getUser()['login'];
    }

    function autologinCondition() {
        return loaded() && getRememberedUser() !== null;
    }

    function loadInit(){
        invalidateSession();
        autologin();
        createAcceptNotice();

    }

let aboutLink = document.getElementById('about');
let about = document.querySelector('.about');
    aboutLink.onclick = () => {
        showAboutWindow();
    }

function showAboutWindow() {
    about.classList.add('show');
    window.scrollTo({ top: 0, behavior: 'smooth' });
    blockBackgroundHtml(true);
}

document.addEventListener("mouseup", (e) => {
    if(about === null || !about.classList.contains('show')) return ;
    if(!about.contains(e.target)) {
        blockBackgroundHtml(false);
        about.classList.remove('show');
    }
});


function createAdmins(menu) {
        let item = document.createElement('div');
        item.classList.add('admin', 'item');


        let btn = document.createElement('a');
        btn.innerText = "Admin'";
        btn.classList.add('sub-btn');

        let arrow = document.createElement('i');
        arrow.classList.add('fas', 'fa-angle-right', 'dropdown');
        btn.appendChild(arrow);

        let subMenu = document.createElement('div');
        subMenu.classList.add('sub-menu');

        let add = document.createElement('a');
        add.innerText = "Add book";
        add.classList.add('sub-item');
        add.onclick = () => addBook();

        let update = document.createElement('a');
        update.innerText = "Set book's count";
        update.classList.add('sub-item');
        update.onclick = () => updateBook();

        subMenu.appendChild(add);
        subMenu.appendChild(update);

        item.appendChild(btn);
        item.appendChild(subMenu);

        menu.appendChild(item);
}

bell.onclick = () => { findNotice(noticeContainer); };

function updateNoticesCount() {
        let sessionid = getUser()['sessionid'];
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/notice/count/get",
            headers: {"session": sessionid},
            cache: false,
            dataType: 'json',
            responseType: 'json',
            success: (data) => {
                let dto = JSON.parse(JSON.stringify(data));
                let count = dto['count'];
                let countNode = document.querySelector('.bell .notice-count');
                if (count > 0) {
                    countNode.innerText = count;
                    countNode.style.display = "block";
                }
                else {
                    countNode.style.display = 'none';
                }
            }
        });
}

function findNotice(container) {
        if(!sessionActive()) return ;
        let sessionid = getUser()['sessionid'];
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/notice/get",
                headers: {"session" : sessionid},
                cache: false,
                dataType: 'json',
                responseType: 'json',
                success: function (list) {
                    sortNoticesNodes();
                    let notices = JSON.parse(JSON.stringify(list));
                        if(notices.length !== 0) {
                            for(let notice of notices){
                                if(!noticeAdded(notice)) addNotice(notice);
                        }
                        if(!container.classList.contains('active')) container.classList.add('active');
                    }
                }
            });

}

function addNotice(item) {
        let notices = document.querySelector('.notices');

        let notice = document.createElement('li');
        notice.classList.add("notice");

        let text = document.createElement('span');
        text.classList.add("text");
        text.innerText = item['noticeMessage'];

        let date = document.createElement('p');
        date.classList.add('notice-date');
        date.innerText = item['noticeDate'];

        let id = document.createElement('p');
        id.classList.add('notice-id');
        id.innerText = item['id'];

        let btn = document.createElement('a');
        btn.innerText = "mark as read";
        btn.classList.add('n-btn');
        if(item['status'] !== 'Read'){ btn.onclick = () => markAsRead(btn, id.innerText); }
        else { setBtnViewToRead(btn); }

        notice.appendChild(text);
        notice.appendChild(date);
        notice.appendChild(id);
        notice.appendChild(btn);
     

        notices.appendChild(notice);
}

function noticeAdded(notice){
        let noticeId = notice['id'];
        let ides = document.querySelectorAll('.notices .notice .notice-id');
        for(let id of ides){
            if(Number(noticeId) === Number(id.innerText)) return true;
        }
        return false;
}

function markAsRead(btn, id) {
        let noticeDto = { "id" : id };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/notice/status/set",
        data: JSON.stringify(noticeDto),
        cache: false,
        dataType: 'json',
        success : () => {
            setBtnViewToRead(btn);
        }
    });
}

function setBtnViewToRead(btn) {
        btn.classList.add('non-active');
        btn.innerText = 'already read';
}

document.addEventListener('click', (e) => {
            if(!noticeContainer.classList.contains('active')) return ;
            if(!noticeContainer.contains(e.target)) {
                noticeContainer.classList.remove('active');
            }
});


function sortNotices(n1, n2){
    let id1 = Number(n1.children[2].innerText);
    let id2 = Number(n2.children[2].innerText);
    let result = 0;
    if(id1 > id2) {
        result = 1;
    }
    
    else {
        result =  - 1;
    }
    return result;
    
}

function sortNoticesNodes(){
    let notices = document.querySelectorAll('.notice-container ol li');
    let array = [];
    for(let notice of notices){
        array.push(notice);
    }
    array.sort((i1, i2) => { return sortNotices(i1, i2); });
    
    let index = 0;
    while(index < notices.length){
        let newNode = array[index];
        let oldNode = notices.item(index);
      
        noticeContainer.children[0].replaceChild(newNode, oldNode);
        
        index ++ ;
   
}
}


