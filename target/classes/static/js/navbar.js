$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    })

});

function controlSidebar() {
    document.querySelector('.sidebar').classList.toggle('active');
    if(window.location.pathname.split('/').pop() === 'home'){
        document.querySelector('.books-slider').classList.toggle('right');
        document.querySelector('.control-slider').classList.toggle('left');
    }
    document.querySelector(".container-fluid").classList.toggle('compression');
    document.querySelector("#menu").classList.toggle('compression');

}

document.querySelector("#side-menu").addEventListener("click", (e) => {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/instance/popular/categories",
        cache: false,
        dataType: 'json',
        responseType : 'json',
        success: function (data) {
            let categories = JSON.parse(JSON.stringify(data))
            addOrUpdate(categories)
        }

    });
})

function addOrUpdate(categories){
    let newNode;
    let currentNode;
    let childrenNodes  = document.querySelector('.sub-menu').children;
    if(childrenNodes.length < categories.length){
        for(let i = childrenNodes.length; i < categories.length; i ++){
            newNode = document.createElement('a');
            newNode.innerText = categories[i]['category'];
            document.querySelector('.sub-menu').appendChild(newNode);
        }
    }
    else {
        for (let i = 0; i < categories.length; i++) {
            newNode = document.createElement('a');
            newNode.innerText = categories[i]['category'];
            currentNode = childrenNodes.item(i);
            document.querySelector('.sub-menu').replaceChild(newNode, currentNode);
        }
    }
}




let categories = document.querySelectorAll('.sub-item');
for(let i = 0; i < a.length; i ++){
    categories[i].addEventListener('click', (e) => {
        let category = categories[i].innerText.toLowerCase();
        executeQuery(category)
    });
}

    document.getElementById('search').addEventListener('click', (e) => {
        let text = document.getElementById('search-input').value;
        executeQuery(text)
    });


let sideLinks = document.querySelectorAll(".main");
    for(let i = 1; i < sideLinks.length - 1; i ++){
        sideLinks[i].addEventListener('click', (e) => {
            if(!containsUserInSession()){
                alert("This options is access only for logged users")
            }
            else {
                let url = extractUrl(sideLinks[i])
                toPage(url)
            }
        })
    }

let topLinks = document.querySelectorAll('.nav-link');
for(let i = 0; i < topLinks.length; i ++){
    topLinks[i].addEventListener('click', (e) => {
        if(!containsUserInSession()){
            alert("This options is access only for logged users")
        }
        else {
            let url = extractUrl(topLinks[i])
            toPage(url)
        }
    })
}

    function containsUserInSession(){
        let sessionEmpty;
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/session",
            cache: false,
            dataType: 'json',
            responseType : 'json',
            success: function (data) {
                sessionEmpty = JSON.parse(JSON.stringify(data));
            }

        });
    return sessionEmpty;

}

    function toPage(url) {
        document.location.href = url;
    }

    function extractUrl(optionsName) {
        if (optionsName.innerText === 'Home') {
            return '/home'
        } else if (optionsName.includes('Cart')) {
            return '/home/cart'
        } else {
            return "/home/" + optionsName.innerText.toLowerCase();
        }
    }

    function executeQuery(value) {
        if (value !== null) {
            document.location.href = '/home/books/search?query=' + value + "&type=popularity";
        }
    }

function clearForms(){
    let elements = document.querySelectorAll('.form-element');
    for(let i = 0; i < elements.length; i ++){
        elements[i].children.item(1).value = "";
    }
    deleteErrorMessages();
}




