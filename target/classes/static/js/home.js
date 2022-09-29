let sliderControlBtn = document.querySelectorAll('.control-slider span');
let books = document.querySelectorAll('.card');
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




let wishListBtn = document.querySelectorAll('.wishlist.btn');
for(let btn of wishListBtn) {
    btn.addEventListener('click', () => {
        let isbn = btn.parentNode.parentNode.children[1].children[3];
        let bookDto = {
            "isbn": isbn.innerText,
        }
        if (!containsInWishlist(bookDto)) {
            navigator.sendBeacon('/home/wishlist/add', bookDto['isbn'])
            fullHeart(btn, true)
        } else {
            navigator.sendBeacon('/home/wishlist/remove', bookDto['isbn'])
            fullHeart(btn, false)
        }
    })
}


    function containsInWishlist(bookDto) {
        let wishlistDto;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/home/wishlist/contains',
            cache: false,
            dataType: 'json',
            responseType: 'json',
            data: JSON.stringify(bookDto),
            async: false,
            success: (wishlist) => {
                wishlistDto = JSON.parse(JSON.stringify(wishlist));
            }
        })
        return wishlistDto['contains'];
    }

    function fullHeart(btn, flag) {
    let heart = btn.children[0];
        if (flag) {
            if(heart.classList.contains('far')){
                heart.classList.remove('far');
                heart.classList.remove('fa-heart');
            }
            btn.children[0].classList.add('fa-solid', 'fa-heart')
        } else {
            if(heart.classList.contains('fa-solid')){
                heart.classList.remove('fa-solid');
                heart.classList.remove('fa-heart');
            }
            btn.children[0].classList.add('far', 'fa-heart')
        }
    }

document.addEventListener('DOMContentLoaded', () => {
    for(let btn of wishListBtn){
        let isbn = btn.parentNode.parentNode.children[1].children[3];
        let bookDto = {
            "isbn" : isbn.innerText
        }
        if(containsInWishlist(bookDto)){
            btn.children[0].classList.add('fa-solid', 'fa-heart')
        }
        else {
            btn.children[0].classList.add('far', 'fa-heart')
        }


    }
})
