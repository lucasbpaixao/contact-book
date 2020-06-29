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
             <div class="list-group-item list-group-item-action" id="contact${contact.id}">
                <img src="imgs/pessoa.png" style="height: 40px; width: 40px; cursor: pointer;" class="mr-2 rounded-circle" onclick="viewContact(${contact.id})"> ${contact.name}
                <i class="material-icons float-right" style="cursor: pointer;" onclick="deleteContact(${contact.id})">delete</i>
             </div>
         `
    ).join('')}
    `;
}

async function deleteContact(id){

    const host = window.location.host;
    const protocol = window.location.protocol;

    const url = protocol + "//" + host + "/contact_book/"+id;

    let res = await fetch(url, {
        method: "DELETE"
    });

    if(res.status == 200){
        showMessage('Contato Excluido com Sucesso!', 'success');
        removeElement(id);
    }else{
        showMessage('Não foi Possivel Excluir o Contato!', 'danger')
    }
}

function removeElement(id){
    const node = document.getElementById("contact"+id);

    node.parentNode.removeChild(node);
}

function viewContact(id){
    window.location.replace('contact/'+id);
}