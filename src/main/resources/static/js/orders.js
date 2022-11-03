let orderDetails = document.querySelectorAll('.order-container .order-details-container');
let detailsBtn = document.querySelectorAll('.details-btn');
let closeDetailsBtn = document.querySelectorAll('.order-details-container .close-details-btn');

for(let i = 0; i < detailsBtn.length; i ++){
    detailsBtn[i].onclick = () => {
        orderDetails[i].classList.add('active');
        detailsBtn[i].style.pointerEvents = 'none';
    }
}

for(let i = 0; i < closeDetailsBtn.length; i ++){
    closeDetailsBtn[i].onclick = () => {
        orderDetails[i].classList.remove('active');
        detailsBtn[i].style.pointerEvents = 'auto';
    }
}
