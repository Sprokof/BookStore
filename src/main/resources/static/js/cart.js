import {getUser} from "./navbar.js";

let homeBtn = document.getElementById('home-btn');
homeBtn.onclick = () => {
    document.location.href = "/";
}

let removeBtn = document.querySelectorAll('.remove-btn');
removeBtn.forEach((btn) => {
    btn.onclick = () => {
    let isbn = btn.parentNode.children[0].children[0].children[1].children[0];
    removeItem(isbn);
    }
})

function removeItem(isbn) {
    console.log(extractISBN(isbn));
    let cartDto = {
        "isbn": extractISBN(isbn),
        "sessionid": getUser()['sessionid'],
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/cart/remove",
        data: JSON.stringify(cartDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success : () => {
           setTimeout(reloadPage, 300);
        }
    })
}


function extractISBN(isbn){
    let startIndex = 7;
    let length = (isbn.innerText.length - startIndex);
    return isbn.innerText.substr(startIndex, length);
}

function reloadPage(){
    window.location.reload();
}