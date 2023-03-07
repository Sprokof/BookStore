import {markAsRead} from "./navbar.js";

let allNotices = document.querySelectorAll('.notifications-container #all .notice');

document.addEventListener("DOMContentLoaded", () => {
    for (let notice of allNotices) {
        let status = notice.children[3].innerText;
        let btn = notice.children[2];
        if (status === 'Read' || status === 'Old') {
            btn.classList.add("non-active");
        } else {
            btn.onclick = () => {
                let id = notice.children[4].innerText;
                markAsRead(btn, id)
            };
        }
    }
})



