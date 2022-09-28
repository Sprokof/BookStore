import {getBookTitle} from "./cards.js";

let sliderControlBtn = document.querySelectorAll('.control-slider span');
let books = document.querySelector('section .card').children;
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

let infos = document.querySelectorAll('.book-info');
for(let i = 0; i < infos.length; i ++){
    infos[i].onclick = () => {
        let child = infos[i].firstChild;
        document.location.href = '/home/book?title=' +
            child.innerText.replaceAll('\s', '').toLowerCase();
    }
}


let cartBtn = document.querySelectorAll('.cart');
cartBtn.forEach((btn) => {
    btn.addEventListener('click', () => {
        if (btn.children.length !== 0) return;
        let url = "/home/cart/add";
        let btnText = btn.innerText;
        if (btnText === "Remove from cart") {
            url = "/home/cart/remove";
        }
        let title = getBookTitle(btn);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: url,
            cache: false,
            dataType: "text",
            data: title,
            success: function () {
                if (btnText === "Remove from cart") {
                    btnText = "Add to cart";
                } else {
                    btnText = "Remove from cart";
                }
            }
        })

    })
})

