import { controlWishlistContent, controlWishlistContentOnLoad,
    controlCartContentOnLoad, controlCartContent,
} from "./main.js";



let sliderControlBtn = document.querySelectorAll('.control-slider span');
let books = document.querySelectorAll('.card');
let book_page = Math.ceil(books.length/4);
let left = 0;
let sliderStep = 25.20;
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
    let title = infos[i].children[0];
    title.onclick = () => {
        let isbn = infos[i].children[3];
        document.location.href = '/home/book?isbn=' + isbn.innerText;
    }
}



let wishListBtn = document.querySelectorAll('.wishlist.btn');
for(let btn of wishListBtn) {
    controlWishlistContent(btn, "home");
}

let cartBtn = document.querySelectorAll('.cart.btn');
for(let btn of cartBtn){
    controlCartContent(btn, "home");
}

document.addEventListener('DOMContentLoaded', () => {
    wishListBtn.forEach(btn => controlWishlistContentOnLoad(btn));
    cartBtn.forEach(btn => controlCartContentOnLoad(btn, "home"));
})


