import {markAsRead} from "./navbar";

let allNotices = document.querySelectorAll('.notifications-container #all .notice');
let readNotices = document.querySelectorAll('.notifications-container #read .notice');

document.addEventListener("DOMContentLoaded", () => {
    for (let notice of allNotices) {
        let status = notice.children[3].innerText;
        let btn = notice.children[2];
        if (status === 'Read' || status === 'Old') btn.classList.add("non-active");
        btn.onclick = () => {
            let id = notice.children[4].innerText;
            markAsRead(btn, id)
        };
    }
})



