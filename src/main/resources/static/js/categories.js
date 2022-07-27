document.querySelector(".select-btn").addEventListener('click', (e) => {
    let display = document.querySelector(".list-items").style.display;
    let degrees = 0;
    switch (display){
        case ('none'):
            display = 'block';
            degrees = 180;
            decrease();
            break;
        case('block'):
            display = 'none';
            degrees = 360;
            increase();
            break;
        default:
            display = 'none';
            decrease();
    }
    document.querySelector('.list-items').style.display = display;
    rotate(degrees)

})
    function rotate(degrees) {
        let arrow = document.getElementById("arrow");
        arrow.style.transform = "rotate(" + degrees + "deg)";
        arrow.style.transition = "0.3s";

}

items = document.querySelectorAll(".item")
let categories;
items.forEach(item => {
    item.addEventListener('click', () => {
        item.classList.toggle("select");

        let selectedCategories = document.querySelectorAll(".select");
        categories = [selectedCategories.length];
        for(let i = 0; i < selectedCategories.length; i ++){
            categories[i] = selectedCategories[i].innerText.toLowerCase();
        }
        let btnText = document.querySelector(".btn-text");
        let length = selectedCategories.length;
        let text;
        if(length > 0) {
            if(length === 1){
                text = "1 category selected";
            }
            else {
                text = (length + " categories selected");
            }
        }
        else {
            text = "Select Books Categories";
        }
        btnText.innerText = text;

    })
})

    let selectBtn = document.querySelector('.select-btn');
    selectBtn.document.addEventListener('click', (e) => {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/instance/all/categories",
            cache: false,
            dataType: 'json',
            responseType : 'json',
            success: function (data) {
                let categories = JSON.parse(JSON.stringify(data));
                addCategory(categories);
        }
    })
})
    function addCategory(categories) {
        let childrenNodes = document.querySelector('.item').children
        if(childrenNodes.length < categories){
            for(let i = childrenNodes.length; i < categories.length; i ++){
                let item = document.createElement('span')
                item.classList.add('item-text')
                item.innerText = categories[i]['category'];
                document.querySelector('.item').appendChild(item)
            }
        }
    }

    function increase() {
        let width = document.querySelector('#container-select').style.width;
        document.querySelector('#submit').style.marginTop = (Number(width) + 20);
    }

    function decrease(){
        document.querySelector('#submit').style.marginTop = 0;

    }

