let address = document.querySelector('#address');
let country = document.querySelector('#country');
let city = document.querySelector('#city');


export function test() {
    let searchAddress = (address.value + ", " + city.value + ", " + country.value);
    let checkoutDto = {
        "address" : searchAddress
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/validate/checkout",
        data: JSON.stringify(checkoutDto),
        cache: false,
        dataType: 'json',
        responseType: 'json',

    })
}