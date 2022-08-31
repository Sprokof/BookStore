function controlSublist(){
    document.querySelector('.sort-types').classList.toggle("active")
    document.querySelector('.arrow-down').classList.toggle('rotate')
}

document.querySelector('.select-btn').addEventListener('click', () => {
    let defaultType = "Choice Sort Type";
    let type;
    type = document.querySelector('.btn-text').innerText;
    document.querySelectorAll('.type').forEach((t) => {
        t.addEventListener('click', () => {
            document.querySelector('.btn-text').innerText = t.children[0].innerText;
            document.querySelector('.sort-types').classList.remove("active")
            document.querySelector('.arrow-down').classList.remove('rotate')
})
    });
    type = document.querySelector('.btn-text').innerText;
        if(type !== defaultType){
        sortBooksLists(type)
    }
});

function sortBooksLists(type){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/last/query/value",
        cache: false,
        dataType: 'text',
        responseType : 'text',
        success: function (query) {
            document.location.href = '/home/books/search?query=' + query + '&type?=' + type;
            }
        }
    )
}



