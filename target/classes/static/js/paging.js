let pages = document.querySelector('#card-container section');
let pagesText = pages.innerText;
let currentPage = Number(extractCurrentPage(pagesText));
let pageCount = Number(extractPagesCount(pagesText));

for(let i = 1; i < pageCount + 1; i ++){
    let page = document.createElement('div');
    page.innerText = String(i);
    pages.appendChild(page);
}
document.addEventListener('DOMContentLoaded', () => {
    pages.innerText = '';
    for(let i = 1; i < pageCount + 1; i ++){
        let page = document.createElement('div');
        page.innerText = String(i);
        if(i === currentPage) page.classList.add('current');
        page.onclick = () => { redirectToPage(page) }
        pages.appendChild(page);
    }
    pages.classList.add('pages');
})

function extractCurrentPage(text){
    let index = text.indexOf(':');
    return text.substr(0, index);
}

function extractPagesCount(text){
    let index = text.indexOf(':') + 1;
    let length = text.length - index;
    return text.substr(index, length);
}

function redirectToPage(page){
    let pageNumber = page.innerText;
    let url = document.location.href;
    let index = url.lastIndexOf("=");
    document.location.href = url.substr(0, index) + '=' + pageNumber;
}