import {getUser} from "./navbar.js";
import {blockBackgroundHtml} from "./notice.js";
import {reload, toBookInfo, validateRequest} from "./main.js";
import {blockInputPointerEvents} from "./checkout.js";

const sumSuffix = ".00 ₽";
const shipping_cost = 170;

let homeBtn = document.getElementById('home-btn');
homeBtn.onclick = () => {
    document.location.href = "/";
}

let removeBtn = document.querySelectorAll('.remove-btn i');
removeBtn.forEach((btn) => {
    btn.onclick = () => {
    let isbnNode = btn.parentNode.parentNode.children[0].
        children[0].children[1].children[0];
    removeItem(isbnNode);
    }
})

function removeItem(isbnNode) {
    let cartDto = {
        "isbn": extractISBN(isbnNode),
        "sessionid": getUser()['sessionid'],
    }

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/cart/item/remove",
        data: JSON.stringify(cartDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success : () => {
           setTimeout(reload, 200);
        }
    })
}


function extractISBN(node){
    let isbn = node.innerText;
    let startIndex = 7;
    let length = (isbn.length - startIndex);
    return isbn.substr(startIndex, length);
}


let inputBlocks = document.querySelectorAll('.quantity .input-block');
inputBlocks.forEach(block => {
    let input = block.children[1];
    block.children[0].onclick = () => {
        decrementItemsQuantity(input);
    }
    block.children[2].onclick = () => {
        incrementItemsQuantity(input);
    }
})


function setItemTotal(input){
    let priceField = input.parentNode.parentNode.parentNode.children[2];
    let totalField = input.parentNode.parentNode.parentNode.children[3];
    let price = (Number(priceField.innerText.substr(0, priceField.innerText.length - 2)));
    let inputValue = (Number(input.value))
    totalField.innerText = ((price * inputValue) + sumSuffix);
}

function setCartTotal(){
    let cartsItems = document.querySelectorAll('.cart-item');
    let itemTotal = 0;
    for(let item of cartsItems){
        let total = item.children[3].innerText;
        let substrLength = total.length - 5;
        itemTotal += (Number(total.substr(0, substrLength)));
    }
    let subtotal = document.querySelector('.subtotal .text-right div');
    subtotal.innerText = (itemTotal + sumSuffix);
    let cartTotal = document.querySelector('.total .text-right div');
    cartTotal.innerText = ((Number (itemTotal) + shipping_cost) + sumSuffix);

}

let checkoutBtn = document.querySelector('#checkout-btn');
checkoutBtn.onclick = () => {
    window.scrollTo({ top: 135, behavior: 'smooth' })
    if(!checkoutSaved()) {
        setTimeout(checkoutOpen, 150);
    }
    else {
        setTimeout(openCheckoutNotice, 150)
    }
}


export function checkoutOpen(){
    let checkout = document.querySelector('#checkout');
    checkout.classList.add('active');
    let checkoutsInputs = document.querySelectorAll('#checkout input');
    checkoutsInputs.forEach((input) => {
        blockInputPointerEvents(input, false)
    })
    blockBackgroundHtml(true);
}

function checkoutSaved(){
    let saved;
    let sessionid = getUser()['sessionid'];
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/checkout/exist",
        headers: {"session" : sessionid},
        cache: false,
        dataType: "json",
        responseType: "json",
        async: false,
        success: (result) => {
            saved = result;
        }
    })
    return saved;
}



function openCheckoutNotice() {
    let notice = document.querySelector('.checkout-notice');
    notice.classList.add("active");
}

let isbnNodes = document.querySelectorAll('.cart-container .isbn');
for(let isbn of isbnNodes){
    isbn.onclick = () => {
        toBookInfo(extractISBN(isbn));
    }
}



function incrementItemsQuantity(input){
    let currentValue = Number (input.value);
    let newValue = currentValue + 1;
    if(newValue > stockValue(input) || (newValue > 99)) return
    input.value = newValue;
    setQuantity(input)
}

function decrementItemsQuantity(input) {
    let currentValue = Number (input.value);
    let newValue;
    if((newValue = (currentValue - 1)) <= 0) return
    input.value = newValue;
    setQuantity(input)
}


function setQuantity(input){
    let isbnNode = input.parentNode.parentNode.parentNode.children[0].
        children[0].children[1].children[0];
    let cartItemDto = {
        "isbn" : extractISBN(isbnNode),
        "quantity" : input.value,
        "sessionid" : getUser()['sessionid']
    }

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/cart/item/set",
        data: JSON.stringify(cartItemDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: () => {
            setItemTotal(input);
            setCartTotal();
        }
    })
}

function stockValue(input){
    let stock = input.parentNode.parentNode.parentNode.children[0].
        children[0].children[1].children[4];
    return Number(stock.innerText.substr(11,  stock.innerText.length));
}

document.addEventListener("DOMContentLoaded", () =>  { validateRequest();} )
