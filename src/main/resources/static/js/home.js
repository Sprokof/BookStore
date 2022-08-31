let sliderControlBtn = document.querySelectorAll('.control-slider span')
let books = document.querySelectorAll('.book');
let book_page = Math.ceil(books.length/4);
let left = 0;
let sliderStep = 25.34;
let maxMove = (sliderStep * (books.length - 4));

let rightMover = () => {
    left = (left + sliderStep);
    if (book_page === 1) {
        left = 0;
    }
    for (const item of books) {

        if (left > maxMove) {
            left = (left - sliderStep);
        }
        item.style.left = '-' + left + '%';
        if(left === maxMove){
            return;
        }
    }
}

let leftMover = () => {
    left = (left - sliderStep);
    if(left <= 0){
        left = 0;
    }
    for (const item of books) {
        if (book_page > 1) {
            item.style.left = '-' + left + '%';
        }
    }
}

sliderControlBtn[1].onclick = () => {
    rightMover();
}

sliderControlBtn[0].onclick = () => {
    leftMover();
}

let titles = document.querySelectorAll('.book-desc p b');
for(let i = 0; i < titles.length; i ++){
    titles[i].onclick = () => {
        let title = titles[i].innerText;
        document.location.href = '/home/book?title=' + title;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    let booksRatings = document.querySelectorAll('.rating');

    for (let i = 0; i < booksRatings.length; i++) {
        let stars = []
        for (let l = 0; l < 10; l++) {
            stars[l] = document.createElement('li');
            if (l < (Number(booksRatings[i].innerText))) {
                stars[l].classList.add('fas', 'fa-star');
            } else {
                stars[l].classList.add('far', 'fa-star');
            }
        }
        booksRatings[i].innerText = '';
        for (let star of stars) {
            booksRatings[i].appendChild(star);
        }
    }
});

let buttons = document.querySelectorAll('.book-button button');
for(let button of buttons) {
    button.addEventListener('click', () => {
        let url = "/home/cart/add";
        let buttonText = button.innerText;
        if (buttonText.includes('wishlist', 0)) {
            url = '/home/wishlist/add';
        }
        let className = button.parentNode.parentNode.className;
        let bookTitle = document.querySelector('.' + className + ' p b').innerText;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: url,
            cache: false,
            dataType: 'text',
            data: bookTitle,
            success: function (data) {
                console.log(data);
            }
        })
    })
}
