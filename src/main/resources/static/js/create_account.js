
const txtUsername = document.getElementById("username");
const txtName = document.getElementById("name");
const txtPassword = document.getElementById("password");
const txtConfirmPassword = document.getElementById("confirmPassword");

function extractDataFromForm() {
    const username = txtUsername.value;
    const name = txtName.value;
    const password = txtPassword.value;
    const confirmPassword = txtConfirmPassword.value;

    const user = `{
        "login": "${username}",
        "password": "${password}",
        "name": "${name}"
    }`;

    if(password == confirmPassword){
        postUser(user);
    }else{
        showMessage("As Senhas São Diferentes!", "danger");
    }
    
}

async function postUser(user) {

    const host = window.location.host;
    const protocol = window.location.protocol;

    const url = protocol + "//" + host + "/create_account";

    const headers = new Headers();
    headers.append("Content-Type", "application/json");

    let res = await fetch(url, {
        headers: headers,
        method: "POST",
        body: user
    });

    if(res.status == 201){
        showMessage('Conta Criada com Sucesso!', 'success');
        clearInputs();
    }else{
        console.log(res);
        showMessage('Não foi Possivel Criar a Conta!', 'danger')
    }

}

function showMessage(message, type){
    const alert = document.getElementById("alert");
    alert.innerHTML = '';
    alert.insertAdjacentHTML('beforeend', alertTemplate(message, type));
}

function alertTemplate(message, type){
    return `<div class="alert alert-${type}" role="alert">
              ${message}
            </div>`
}

function clearInputs(){
    txtName.value = "";
    txtUsername.value = "";
    txtPassword.value = "";
    txtConfirmPassword.value = "";
}