import {getUser} from "./navbar.js";
import {extractISBN, reload} from "./main.js";
import {blockBackgroundHtml} from "./notice.js";

let postReviewBtn = document.querySelector('#book-info .post-btn');
postReviewBtn.onclick = () => {
    let bookReviewDto = {
        "review": document.querySelector('#book-info .review-text').value,
        "score": document.querySelector('#book-info .score input').value,
        "isbn": extractISBN(document.querySelector('#book-info .isbn')),
        "sessionid": getUser()['sessionid'],

    }
    postReview(bookReviewDto);
}

function postReview(review) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/reviews/post",
        cache: false,
        data: JSON.stringify(review),
        dataType: 'json',
        responseType: 'json',
        success: () => { closeReviewWindow(); }
        })
}

function closeReviewWindow() {
    let reviewWindow = document.querySelector('.reviews-container');
    reviewWindow.classList.remove('active');
    setTimeout(reload, 200);
    blockBackgroundHtml(false);
}



