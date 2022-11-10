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
    if(!valid()) return ;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/reviews/post",
        cache: false,
        data: JSON.stringify(review),
        dataType: 'json',
        responseType: 'json',
        success: () => { closeReviewWindow (); }
        })
}
let reviewWindow = document.querySelector('.review-container');
let scoreField = document.querySelector('.review-container .score input');

function closeReviewWindow () {
    reviewWindow.classList.remove('active');
    setTimeout(reload, 250);
    blockBackgroundHtml(false);
    clearReviewForm();

}

scoreField.onkeyup = () => {
    let value = scoreField.value;
    if(value === '') deleteWrongClass();
    let valid = (value.match(/^\d+$/) != null && (Number (value) >= 1 && Number (value) <= 5));
    if (valid) {
        deleteWrongClass();
    } else {
        scoreField.classList.add('wrong');
    }
}


function clearReviewForm () {
    reviewWindow.children[0].value = '';
    reviewWindow.children[1].children[0].value = '';
    deleteWrongClass();
}

function valid(){
    return !scoreField.classList.contains('wrong');
}

function deleteWrongClass() {
    if(scoreField.classList.contains('wrong')){
        scoreField.classList.remove('wrong');
    }
}