let sliderControlBtn = document.querySelectorAll('.control-slider span')
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

let titles = document.querySelectorAll('.book-info span');
for(let i = 0; i < titles.length; i ++){
    titles[i].onclick = () => {
        let title = titles[i].innerText;
        document.location.href = '/home/book?title=' + title;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    let booksRatings = document.querySelectorAll('.star-rating');
    let fullCountStar = 5;

    for (let i = 0; i < booksRatings.length; i++) {
        let stars = []
        let decimalPart = 0;
        let wholePart = 0;
        for (let l = 0; l < fullCountStar; l++) {
            stars[l] = document.createElement('li');
            wholePart = (Number(booksRatings[i].innerText.substr(0, 1)));
            decimalPart = (Number(booksRatings[i].innerText.substr(2)));
            if (l < wholePart) {
                stars[l].classList.add('fas', 'fa-star');

            } else {
                stars[l].classList.add('far', 'fa-star');
            }
        }
        if (decimalPart <= 7 && decimalPart >= 4) {
            stars[wholePart].classList.replace('far', 'fas');
            stars[wholePart].classList.replace('fa-star', 'fa-star-half');

        } else if (decimalPart >= 8) {
            stars[wholePart].classList.replace('far', 'fas');
        }

        let text = booksRatings[i].innerText;
        booksRatings[i].innerText = '';
        for (let star of stars) {
            booksRatings[i].appendChild(star);
        }
        let span = document.createElement('span');
        span.classList.add('numeric-rating')
        span.innerText = text;
        booksRatings[i].appendChild(span);

    }
})


let cartBtn = document.querySelectorAll('.cart');
cartBtn.forEach((btn) => {
    btn.addEventListener('click', () => {
        let btnText = btn.innerText;
        let title = getBookTitle(btn);
        let url = "/home/cart/add";
        if (btnText === "Remove from cart") {
            url = "/home/cart/remove";
        }
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

    let wishlistBtn = document.querySelectorAll('.wishlist');
    wishlistBtn.forEach((btn) => {
        btn.addEventListener('click', () => {
            let url = "/home/wishlist/add";
            let children = btn.children[0];
            if (children.classList.contains("fa-solid")) {
                url = "/home/wishlist/remove";
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
                    if (url.substr(url.lastIndexOf("/")) === "remove") {
                        children.classList.replace("fa-solid", "far");
                    } else {
                        children.classList.replace("far", "fa-solid");
                    }
                }

            })
        })
    })
})

    function getBookTitle(btn) {
        let bookInfo = btn.parentNode.parentNode;
        return bookInfo.firstChild.innerText;

    }

