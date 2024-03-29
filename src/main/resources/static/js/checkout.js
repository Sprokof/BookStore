import {getUser} from "./navbar.js";
import {closeSuccessWindow, openSuccessWindow} from "./window.js";
import {reload} from "./main.js";
import {checkoutOpen} from "./cart.js";
import {blockBackgroundHtml} from "./notice.js";

let noticeContainer = document.querySelector('.checkout-notice');


let purchase = document.querySelector('#purchase');
if(purchase != null) {
    purchase.onclick = () => {
        let checkoutDto = {
            "firstName": document.querySelector('#f-name').value,
            "lastName": document.querySelector('#l-name').value,
            "street" : document.querySelector('#address').value,
            "country": document.querySelector('#country').value,
            "city" : document.querySelector('#city').value,
            "address": addressFormatting(),
            "zip": document.querySelector('#zip').value,
            "cardNumber": getCardNumber(),
            "exp": document.querySelector('#exp').value,
            "ccv": document.querySelector("#ccv").value,
            "sessionid" : getUser()['sessionid']
        }
        checkoutValidation(checkoutDto);
    }
}

function addressFormatting () {
    let address = document.querySelector('#address').value;
    let country = document.querySelector('#country').value;
    let city = document.querySelector('#city').value;
    if(address === '' || country === '' || city === '') return '';

    return (address + ", " +  city + ", " + country);
}



export function executePurchase(){
    let orderDto = {
        "sessionid": getUser()['sessionid'],
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/orders/add",
        data: JSON.stringify(orderDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success : () => {
            clearCart();
        }
    })
}

export function successWindowControl(){
    let success = document.querySelectorAll('#success-window')[1];
    success.style.top = "25%";
    success.style.left = "30%";
    setTimeout(openSuccessWindow, 100);
    setTimeout(closeSuccessWindow, 2150);
    setTimeout(reload, 2500);
}

export function clearCart(){
    let cartDto = {
        "sessionid" : getUser()['sessionid']
    }
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/cart/clear",
        data: JSON.stringify(cartDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: () => {
            successWindowControl();
        }
    })
}

function checkoutValidation(checkoutDto) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/validate/checkout",
        data: JSON.stringify(checkoutDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',
        success: (data) => {
            let validationErrors = JSON.parse(JSON.stringify(data));
            let errorsMap = new Map(Object.entries(validationErrors));
            if(errorsMap.size > 0) {
                highlightFields(errorsMap);
            }
            else {
                executePurchase();
            }
        }
    })
}

function highlightFields(map){
    for(let [fieldId, value] of map){
        let field = document.getElementById(fieldId);
        field.classList.add('highlight');
        controlErrorMessage(field, value);
    }
}

function controlErrorMessage(field, value){
    let saveValue = field.value;
    field.onmouseover = () => {
        if(!field.classList.contains('highlight')) return ;
        field.value = value;
        field.style.color = "red";
    }
    field.onmouseout = () => {
        if(!field.classList.contains('highlight')) return ;
        field.value = saveValue;

    }
    field.oninput = () => {
        if(!field.classList.contains('highlight')) return ;
        field.value = "";
        field.classList.remove('highlight');
        field.style.color = "black";
    }
}

    let createNewBtn = document.querySelectorAll('.checkout-notice .buttons button')[1];
    if(createNewBtn != null) {
        createNewBtn.onclick = () => {
            closeCheckoutNotice();
            checkoutOpen();
        }
    }

    let savedInfoBtn = document.querySelectorAll('.checkout-notice .buttons button')[0];
    if(savedInfoBtn != null) {
        savedInfoBtn.onclick = () => {
            fillCheckoutInputs();
            closeCheckoutNotice();
            checkoutOpen();
        }
    }

    function fillCheckoutInputs() {
        let checkoutValues = getCheckoutData();
        let inputs = document.querySelectorAll('#checkout input');
        for(let i = 0; i < checkoutValues.length; i ++){
            inputs[i].value = checkoutValues[i];
            if(i < (checkoutValues.length - 1))
            blockInputPointerEvents(inputs[i], true);
        }
    }

    function getCheckoutData() {
        let responseDto;
        let sessionid = getUser()['sessionid'];
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/checkout/data",
            headers: {'session' : sessionid},
            cache: false,
            dataType: 'json',
            responseType: 'json',
            async : false,
            success: (dto) => {
                responseDto = JSON.parse(JSON.stringify(dto));
            }
        })

        let fieldArray = [];
        for(let value of Object.values(responseDto)){
            if(value == null) continue;
            fieldArray.push(value);
        }
        return fieldArray;
    }

export function blockInputPointerEvents(input, flag) {
    let value = "none";
    if (!flag) value = "auto";
    input.style.pointerEvents = value;
}


let numberField = document.querySelector('#card-num');
    if(numberField != null) {
        let counter = 0;
        numberField.onkeyup = () => {
            counter++;
            if (counter === 4) {
                numberField.value += ' ';
                counter = 0;
            }
        }
    }

function getCardNumber() {
        return numberField.value.substr(0, 19);
}

export function checkoutClose(){
    let checkout = document.querySelector('#checkout');
    clearCheckoutInputs();
    checkout.classList.remove('active');
    blockBackgroundHtml(false);

}

function clearCheckoutInputs(){
    let inputs = document.querySelectorAll('.checkout-container div input');
    inputs.forEach((input) => {
        input.value = '';
        input.classList.remove('highlight');
    });
}

let closeCheckoutBtn = document.querySelectorAll('#checkout .buttons button')[0];
closeCheckoutBtn.onclick = () => {
    setTimeout(checkoutClose, 150);
}

document.addEventListener('mouseup', (e) => {
    if (noticeContainer.classList.contains('active') &&
        !noticeContainer.contains(e.target)) {
        closeCheckoutNotice();
    }
})

function closeCheckoutNotice(){
    noticeContainer.classList.remove('active');
}