let btn = document.querySelector('.select-btn');
btn.onclick = () => {
    document.querySelector('.sort-types').classList.toggle("active");
    document.querySelector('.arrow-down').classList.toggle('rotate');
}

let btnText = document.querySelector('.btn-text');
let sortTypes = document.querySelectorAll('.type');
sortTypes.forEach((type) => {
    type.addEventListener("click", () => {
        let selectedType = type.children[0].innerText;
        sortBooksLists(selectedType);
    })
})

document.addEventListener("DOMContentLoaded", () => {
    saveParams();
    let type = modifyType(getSearchParams()['type']);
    if(type !== "Popularity"){
        btnText.innerText += (" " + (type.substr(0, 9) + " ..."));
    }
    else {
        btnText.innerText += (" " + type);
    }
    highlightSelectedType(type);

})

function sortBooksLists(type){
    let searchParams = getSearchParams();
    document.location.href = '/books/search?query=' +
        searchParams['query'] + '&type=' + type.toLowerCase() + "&page=" + searchParams['page'];


}

function getSearchParams(){
    let searchParams = sessionStorage.getItem("params");
    return JSON.parse(searchParams);
}

function highlightSelectedType(currentType){
    let sorTypes = document.querySelectorAll('.type');
       for(let type of sorTypes) {
           let typeName = type.children[0].innerText;
           if (typeName.trim() === currentType) {
               type.classList.add('select');
           } else {
               type.classList.remove('select');
           }
       }
}

function saveParams() {
    let url = document.location.href;
    let params = {
        "query" : extractQuery(url),
        "type" : extractType(url),
        "page" : extractPageNumber(url),
    }
    sessionStorage.setItem("params", JSON.stringify(params));
}

function extractQuery(url){
    let index = url.indexOf("=") + 1;
    let lastIndex = url.indexOf("&");
    let length = (lastIndex - index);
    return url.substr(index, length);
}


function extractType(url) {
    let index = url.indexOf("&") + 6;
    let lastIndex = url.lastIndexOf("&");
    let length = (lastIndex - index);
    return url.substr(index, length);

}

function extractPageNumber(url) {
    let index = url.lastIndexOf("=") + 1;
    let urlLength = url.length;
    let length = (urlLength - index) ;
    return url.substr(index, length);
}


function modifyType(type){
    return capitalize(type.replaceAll("%20", " "));

}

function capitalize(str){
    let result = "";
    for(let s of str.split(" ")){
        result += s.charAt(0).toUpperCase() + s.slice(1) + " ";
    }
    return result.trim();
}