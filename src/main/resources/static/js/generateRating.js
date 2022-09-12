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
            decimalPart = (Number(booksRatings[i].innerText.substr(2,1)));
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