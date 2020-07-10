
function extractDataFromForm() {
    const contactId = document.getElementById("contactId").value;
    const contactName = document.getElementById("contactName").value;
    const contactEmail = document.getElementById("contactEmail").value;
    const contactPhoneNumber = document.getElementById("contactPhoneNumber").value;

    const contact = `{
        "name": "${contactName}",
        "phoneNumber": "${contactPhoneNumber}",
        "email": "${contactEmail}"
    }`;

    if(contactId < 0){
        post(contact);
    }else{
        put(contactId, contact);
    }
}

async function post(contact) {

    const host = window.location.host;
    const protocol = window.location.protocol;

    const url = protocol + "//" + host + "/contact_book";

    const headers = new Headers();
    headers.append("Content-Type", "application/json");

    let res = await fetch(url, {
        headers: headers,
        method: "POST",
        body: contact
    });

    if(res.status == 201){
        window.location.replace('../../');
        showMessage('Contato Cadastrado com Sucesso!', 'success');

    }else{
        console.log(res);
        showMessage('Não foi Possivel Cadastrar o Contato!', 'danger')
    }

}

async function put(id, contact) {

    const host = window.location.host;
    const protocol = window.location.protocol;

    const url = protocol + "//" + host + "/contact_book/" + id;

    const headers = new Headers();
    headers.append("Content-Type", "application/json");

    let res = await fetch(url, {
        headers: headers,
        method: "PUT",
        body: contact
    });

    if(res.status == 200){
        window.location.replace('../../');
        showMessage('Contato Alterado com Sucesso!', 'success');
    }else{
        showMessage('Não foi Possivel Alterar o Contato!', 'danger')
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

function extractContactId(){
    const url = window.location.href.split('/')
    const id = url[url.length -1];

    registerId(id);

    if(id > 0){
        getContactById(id).then(data => fillsFields(JSON.parse(data)));
    }
}

function fillsFields(contact){
    document.getElementById("labelName").classList.add("active");
    document.getElementById("labelEmail").classList.add("active");
    document.getElementById("labelPhoneNumber").classList.add("active");

    document.getElementById("contactName").value = contact.name;
    document.getElementById("contactEmail").value = contact.email;
    document.getElementById("contactPhoneNumber").value = contact.phoneNumber;
}

function registerId(id){
    document.getElementById("contactId").value = id;
}

async function getContactById(id){
    const host = window.location.host;
    const protocol = window.location.protocol;

    const url = protocol + "//" + host + "/contact_book/"+id;

    let res = await fetch(url, {
        method: "GET"
    });

    return res.text();
}
