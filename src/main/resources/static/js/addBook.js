import {validation} from "./validation.js";

let saveBook = document.querySelector('.save-btn');
saveBook.addEventListener('click', () => {
    let book = {
        "isbn" : document.querySelector('#isbn').value,
        "title" : document.querySelector('#title'),
        "publisher" : document.querySelector('#publisher').value,
        "price" : document.querySelector('#price').value,
        "yearPub" : document.querySelector('#year').value,
        "subject" : document.querySelector('#subject').value,
        "bookImage" : document.querySelector('#file')[0].name,
        "availableCopies" : document.querySelector('#copies').value,
        "description" : document.querySelector('#desc').value,
        "authors" : document.querySelector('#author').value,
        "format" : document.querySelector('#format').value,
        "booksCategories" : selectedCategories()
    }
    validation(book, "/home/book/add");
})


document.querySelector(".select-btn").addEventListener('click', (e) => {
    document.querySelector('.list-items').classList.toggle("active");
    document.querySelector('.fas.fa-chevron-down').classList.toggle('rotate');
});


function selectedCategories() {
    let items = document.querySelectorAll(".item")
    let categories;
    let category = {};
    items.forEach(item => {
        item.addEventListener('click', () => {
            item.classList.toggle('select');

            let selected = document.querySelectorAll(".select");
            categories = [selected.length];
            for (let i = 0; i < selected.length; i++) {
                category['category'] = selected[i].children[1].innerText;
                categories[i] = category;
            }

            let btnText = document.querySelector(".btn-text");
            let length = selected.length;
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
    return categories;
}

$('#file').change(function(){
    let fileResult = $(this).val();
    console.log($(this));
    $(this).parent().find('.fileLoad').find('input').val(fileResult);
});

$('#file').hover(function(){
    $(this).parent().find('button').addClass('button-hover');
}, function(){
    $(this).parent().find('button').removeClass('button-hover');
});
