async function get(){
    const host = window.location.host;
    const protocol = window.location.protocol;

    const url = protocol + "//" + host + "/contact_book";

    let res = await fetch(url, {
        method: "GET"
    });

    listContacts(res.text());
}

function listContacts(promise){
    promise.then((c)=>{
        const contacts = JSON.parse(c);
        updateListOnView(contacts);
    });
}

function updateListOnView(contacts){
    const list = document.getElementById('list');

    if(contacts.length == 0){
        list.insertAdjacentHTML('beforeend', '<h4>Não há contatos registrados no banco de dados!</h4>');
    }else{
        list.insertAdjacentHTML('beforeend', template(contacts));
    }
}

function template(contacts){
    return `
    ${contacts.map(contact =>
        `
         <a onclick="viewContact(${contact.id})" class="list-group-item list-group-item-action">
<img src="imgs/pessoa.png" style="height: 40px; width: 40px;" class="mr-2 rounded-circle"> ${contact.name}
         </a>`
    ).join('')}
    `;
}

function viewContact(id){
    window.location.replace('contact/'+id);
}