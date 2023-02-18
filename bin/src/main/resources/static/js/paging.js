let pages = document.querySelector('#card-container .pages');
let pageButton;
let currentPage;
let lastPage;
if(pages != null) {
    pageButton = pages.children[0];
    currentPage = extractCurrentPage(pages);
    lastPage = extractLastPage(pages);
    let input = pages.children[1].children[0];
    input.oninput = () => {
        let value = input.value;
        console.log(valid(value))
        if (valid(value)) {
            pageButton.classList.add('active');
            toNextPage(value)
        } else {
            if (pageButton.classList.contains('active')) {
                pageButton.classList.remove('active');

            }
        }

    }
}

function extractCurrentPage(pages){
    return pages.children[1].children[0].value;
}

function extractLastPage(pages){
    let text = pages.children[1].children[1].innerText;
    return text.substr(1, (text.length - 1));
}

function valid(value){
    console.log(value)
    if(value === '0') return false;
    return value.match(/^\d+$/) != null
    && Number(value) !== Number(currentPage)
    && Number(value) <= Number(lastPage);
}

function toNextPage(page){
    let url = document.location.href;
    let index = url.lastIndexOf('=');
    pageButton.onclick = () => {
        document.location.href = url.substr(0, index) + "=" + page;
    }
}