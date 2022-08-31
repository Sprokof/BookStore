let addItems = document.querySelectorAll(".col-lg-1 .quantity");
let itemToAdd = {}
    for(let i = 0; i < addItems.length; i ++){
        let cartItemsChildren = addItems[i].children
        for(let i = 0; i < cartItemsChildren.length; i ++){
            cartItemsChildren[i].addEventListener('input' , (e) => {
            itemToAdd['cartItemId'] = cartItemsChildren[i].id.substr(4);
            itemToAdd['quantity'] = cartItemsChildren[i].value
            setCartItem(itemToAdd)
            })
        }
    }

    function setCartItem(cartItem){
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/home/cart/set",
            cache: false,
            dataType: 'json',
            data: JSON.stringify(cartItem)
        })
    }

let removeItems = document.querySelectorAll(".col-lg-1 .remove-item");
let itemToRemove = {}
for(let i = 0; i < removeItems.length; i ++){
    let cartItemsChildren = removeItems[i].children
    for(let i = 0; i < cartItemsChildren.length; i ++){
        cartItemsChildren[i].addEventListener('input' , (e) => {
            ['itemId'] = cartItemsChildren[i].id.substr(6);
            removeCartItem(itemToRemove)
        })
    }
}


function removeCartItem(cartItem){
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/cart/remove",
        cache: false,
        dataType: 'json',
        data: JSON.stringify(cartItem)
    })
}




