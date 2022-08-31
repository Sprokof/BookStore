function blockPointerEvents(html, excludedPart) {
    html.style.pointerEvents = 'none'
    excludedPart.style.pointerEvents = 'auto';
}


let blockScroll = (excludedPart) => {
    let html = document.documentElement;
    let scrollPosition = window.pageYOffset;
    html.style.top = -scrollPosition + 'px';
    html.classList.add('open');
    blockPointerEvents(html, excludedPart)
}

let unblockScroll = () => {
    let html = document.documentElement;
    let scrollPosition = window.pageYOffset;
    html.classList.remove('open');
    window.scrollTo(0, scrollPosition);
    html.style.top = '';
    html.style.pointerEvents = 'auto';

}