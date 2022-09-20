document.querySelector(".select-btn").addEventListener('click', (e) => {
    document.querySelector('.list-items').classList.toggle("active");
    document.querySelector('.fas.fa-chevron-down').classList.toggle('rotate');
});


function selectCategories() {
    let items = document.querySelectorAll(".item")
    let categories;
    let category = {};
    items.forEach(item => {
        item.addEventListener('click', () => {
            item.classList.toggle('select');

            let selected = document.querySelectorAll(".select");
            categories = [selected.length];
            for (let i = 0; i < selected.length; i++) {
                category['category'] = selected[i].children[1].innerText.toLowerCase()
                categories[i] = category;
            }

            //addCategories(categories)

            let btnText = document.querySelector(".btn-text");
            let length = selected.length;
            let text;
            if (length > 0) {
                if (length === 1) {
                    text = "1 category selected";
                } else {
                    text = (length + " categories selected");
                }
            } else {
                text = "Select Books Categories";
            }
            btnText.innerText = text;

        })
    })
}

    let selectBtn = document.querySelector('.select-btn');
    selectBtn.addEventListener('click', (e) => {
       selectCategories()
})

    function fillCategoryMenu(categories) {
        let childrenNodes = document.querySelector('.list-items').children
        if(childrenNodes.length < categories.length){
            for(let i = childrenNodes.length; i < categories.length; i ++){

                let item = document.createElement('li')
                item.classList.add('item')
                let checkbox = document.createElement('span');
                checkbox.classList.add('checkbox')
                let icon = document.createElement('i');
                icon.classList.add('fas', 'fa-check"')
                let category = document.createElement('span')
                category.classList.add('item-text')
                category.innerText = categories[i]['category']
                console.log(category)

                checkbox.appendChild(icon);
                item.appendChild(checkbox)
                item.appendChild(category)

                document.querySelector('.list-items').appendChild(item)
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

$('#file').change(function(){
    let fileResult = $(this).val();
    console.log($(this));
    $(this).parent().find('.fileLoad').find('input').val(fileResult);
});

$('#file').hover(function(){
    $(this).parent().find('button').addClass('button-hover');
}, function(){
    $(this).parent().find('button').removeClass('button-hover');
});



