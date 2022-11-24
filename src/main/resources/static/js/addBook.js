import {validation} from "./validation.js";

let saveBook = document.querySelector('.save-btn');
saveBook.addEventListener('click', () => {
    let book = {
        "isbn" : document.querySelector('#isbn').value,
        "title" : document.querySelector('#title').value,
        "publisher" : document.querySelector('#publisher').value,
        "price" : document.querySelector('#price').value,
        "yearPub" : document.querySelector('#year').value,
        "subject" : document.querySelector('#subject').value,
        "bookImage" : fileName(document.querySelector('#file')),
        "availableCopies" : document.querySelector('#copies').value,
        "description" : document.querySelector('#desc').value,
        "authors" : document.querySelector('#author').value,
        "format" : document.querySelector('#format').value,
        "booksCategories" : getCategories(),
    }
    validation(book, "/book/add");
})


document.querySelector(".select-btn").addEventListener('click', (e) => {
    selectCategory();
    document.querySelector('.list-items').classList.toggle("active");
    document.querySelector('.fas.fa-chevron-down').classList.toggle('rotate');
});


function selectCategory() {
    let items = document.querySelectorAll(".item");
    items.forEach(item => {
        item.addEventListener('click', () => {
            item.classList.toggle('select');

            let btnText = document.querySelector(".btn-text");
            let length =  document.querySelectorAll('.item.select').length;
            let text;
            if (length > 0) {
                if (length === 1) {
                    text = "1 category selected";
                } else {
                    text = (length + " categories selected");
                }
            } else {
                text = "Select Books Categories";
            }
            btnText.innerText = text;

        })
    })
}

$('#file').change(function(){
    let fileResult = $(this).val();
    $(this).parent().find('.fileLoad').find('input').val(fileResult);
});

$('#file').hover(function(){
    $(this).parent().find('button').addClass('button-hover');
}, function(){
    $(this).parent().find('button').removeClass('button-hover');
});

function fileName (file) {
    let start = (file.value.lastIndexOf("\\") + 1);
    return file.value.substr(start);
}

function getCategories(){
    let allCategories = '';
    let selected = document.querySelectorAll(".item.select");
    for (let i = 0; i < selected.length; i++) {
         let category = selected[i].children[1].innerText;
         if(category === '') continue;
         allCategories += selected[i].children[1].innerText + ',';
    }

    return allCategories;
}