document.querySelector(".select-btn").addEventListener('click', (e) => {
    document.querySelector('.list-items').classList.toggle('open');
    document.querySelector('.fas.fa-chevron-down').classList.toggle('rotate');
});


items = document.querySelectorAll(".item")
let categories;
let category = {};
items.forEach(item => {
    item.addEventListener('click', () => {
        item.classList.toggle("select");

        let selectedCategories = document.querySelectorAll(".select");
        categories = [selectedCategories.length];
        for(let i = 0; i < selectedCategories.length; i ++){
            category['category'] = selectedCategories[i].innerText.toLowerCase();
            categories[i] = category;
        }

        addCategories(categories)

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
                fillCategoryMenu(categories);
        }
    })
})
    function fillCategoryMenu(categories) {
        let childrenNodes = document.querySelector('.item').children
        if(childrenNodes.length < categories){
            for(let i = childrenNodes.length; i < categories.length; i ++){

                let item = document.createElement('li')
                item.classList.add('item')
                let checkbox = document.createElement('span');
                checkbox.classList.add('checkbox')
                let icon = document.createElement('i');
                icon.classList.add('fas fa-check"')
                let category = document.createElement('span')
                category.innerText = categories[i]['category']

                checkbox.appendChild(icon);
                item.appendChild(checkbox)
                item.appendChild(category)

                document.querySelector('.item-list').appendChild(item)
            }
        }
    }

    function addCategories(categories) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/home/book/add/category",
            cache: false,
            dataType: 'json',
            data: JSON.stringify(categories)
        });
    }


