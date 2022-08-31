let login = document.getElementById('login-btn');
login.addEventListener('click', () => {
    let user = {};
    user['login'] = document.getElementById('login').value;
    user['password'] = hash(document.getElementById('log-password').value);
    user['remembered'] = document.querySelector('.remember-me input').checked;

    doValidateRequest(user, "/home/login");

});

let registration = document.getElementById("sign-in-btn");
registration.addEventListener("click", () => {
    let user = {}
    user['username'] = document.getElementById('username').value;
    user['email'] = document.getElementById("reg-email").value;
    user['password'] = hash(document.getElementById('reg-password').value);
    user['confirmPassword'] = hash(document.getElementById("confirm-reg-password"));
    user['ipAddress'] = '0';
    user['remembered'] = false;
    doValidateRequest(user, "/home/registration");
});

let reset = document.getElementById("continue-btn");
reset.addEventListener("click", () => {
    deleteErrorMessages();
    let newPassword = document.getElementById("new-password").value;
    let confirmPassword = document.getElementById("confirm-reset-password").value;
    if(newPassword !== confirmPassword){
        let confirmElement = document.getElementById("confirm-reset-password").parentNode;
        let error = document.createElement("p");
        error.classList.add("error-message");
        error.innerText = "Passwords not equals";
        confirmElement.appendChild(error);
    }
    else {
        let hashPassword = hash(newPassword);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            cache: false,
            url: '/home/reset',
            dataType: 'text',
            data: JSON.stringify(hashPassword),
            success: function () {
                resetClose();
                openResetTwoPopup();
            }
        })

    }
})

let confirm = document.getElementById("done-btn");
confirm.addEventListener("click", () => {
    let code = document.getElementById("code").value;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/home/reset/confirm",
        cache: false,
        dataType: 'text',
        responseType: "json",
        data: JSON.stringify(code),
        success: function (data) {
            deleteErrorMessages();
            let correct = JSON.parse(JSON.stringify(data));
            if(!correct){
                let error = document.createElement('p');
                error.classList.add('error-message');
                error.innerText = "Wrong confirmation code";
                document.getElementById("code").parentNode.appendChild(error);
            }
            else {
                closeResetTwoPopup();
            }
        }
    })
})

function doValidateRequest(user, url){
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        cache: false,
        dataType: 'json',
        responseType: "json",
        data: JSON.stringify(user),
        success: function (data) {
            deleteErrorMessages();
            let validationErrors = JSON.parse(JSON.stringify(data));
            if (validationErrors.length > 0) {
                for (let [field, message] of validationErrors) {
                    let wrongInput = document.getElementById(field);
                    let error = document.createElement('p');
                    error.classList.add('error-message');
                    error.innerText = message;
                    wrongInput.parentNode.appendChild(error);
                    let rem = document.querySelector('.remember-me input');
                    rem.checked = false;
                }
            } else {
                   loginClose();
                   signInClose();
            }
        }
    })
}

function deleteErrorMessages(){
    let errorsMessages = document.querySelectorAll(".error-message")
    errorsMessages.forEach(message => message.remove());
}

function hash(string) {
    const utf8 = new TextEncoder().encode(string);
    return crypto.subtle.digest('SHA-256', utf8).then((hashBuffer) => {
        const hashArray = Array.from(new Uint8Array(hashBuffer));
        return hashArray
            .map((bytes) => bytes.toString(16).padStart(2, '0'))
            .join('');
    });
}