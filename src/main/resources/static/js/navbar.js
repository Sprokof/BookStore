$(document).ready(function () {
    $('.sub-btn').click(function (){
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    })

});

function controlSidebar() {
    document.querySelector('.sidebar').classList.toggle('active');
    document.querySelector(".container-fluid").classList.toggle("compression");
    document.querySelector("#menu").classList.toggle("compression");
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


function searchRequest(categoryQuery){
    $.ajax({
        type: "POST",
        dataType: "application/json",
        url: "/home/search?category=" + categoryQuery.category ,
        data: JSON.stringify(categoryQuery)
    });
}

let categories = document.querySelectorAll('.sub-item');
let categoryQuery = {}
for(let i = 0; i < a.length; i ++){
    categories[i].addEventListener('click', (e) => {
        categoryQuery['category'] = categories[i].innerText.toLowerCase();
        searchRequest(categoryQuery);
    });
}