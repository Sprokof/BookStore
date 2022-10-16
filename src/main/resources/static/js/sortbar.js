let btn = document.querySelector('.select-btn');
btn.onclick = () => {
    document.querySelector('.sort-types').classList.toggle("active");
    document.querySelector('.arrow-down').classList.toggle('rotate');
}

let sortTypes = document.querySelectorAll('.type');
sortTypes.forEach((type) => {
    type.addEventListener("click", () => {
        let currentType = type.children[0].innerText;
        sortBooksLists(currentType);

    })
})

let btnText = document.querySelector('.btn-text');
document.addEventListener("DOMContentLoaded", () => {
    let sortType = getSearchParams()['sortType'];
    if(sortType !== "Popularity"){
        btnText.innerText += (" " + (sortType.substr(0, 9) + " ..."));
    }
    else {
        btnText.innerText += (" " + sortType);
    }
    highlightSelectedType(sortType);
})


function sortBooksLists(type){
    let searchParams = getSearchParams();
    document.location.href = '/home/books/search?query=' +
        searchParams['query'] + '&type=' + type.toLowerCase();


}

function getSearchParams(){
    let searchParams;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/search/params",
        cache: false,
        dataType: 'json',
        responseType : 'json',
        async: false,
        success: (params) => {
            searchParams = JSON.parse(JSON.stringify(params));
        }
    })
    return searchParams;
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





