let wishlistBtn = document.querySelectorAll('.wishlist');
wishlistBtn.forEach((btn) => {
    btn.addEventListener('click', () => {
        let url = "/home/wishlist/add";
        let children = btn.children[0];
        if (children.classList.contains("fa-solid")) {
            url = "/home/wishlist/remove";
        }
        let title = getBookTitle(btn);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: url,
            cache: false,
            dataType: "text",
            data: title,
            success: function () {
                if (url.substr(url.lastIndexOf("/")) === "remove") {
                    children.classList.replace("fa-solid", "far");
                } else {
                    children.classList.replace("far", "fa-solid");
                }
            }

        })
    })
})

function getBookTitle(btn) {
    let bookInfo = btn.parentNode.parentNode;
    return bookInfo.firstChild.innerText;

}