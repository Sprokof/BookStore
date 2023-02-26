let allNotices = document.querySelectorAll('.notifications-container #all .notice');
let readNotices = document.querySelectorAll('.notifications-container #read .notice');

document.addEventListener("DOMContentLoaded", () => {
    for (let notice of allNotices) {
        let status = notice.children[3].innerText;
        if (status === 'Read' || status === 'Old') {
            let btn = notice.children[2];
            btn.classList.add("non-active");
        }
    }

});

