
function extractDataFromForm() {
    const contactName = document.getElementById("contactName").value;
    const contactEmail = document.getElementById("contactEmail").value;
    const contactPhoneNumber = document.getElementById("contactPhoneNumber").value;

    const contact = `{
        "name": "${contactName}",
        "phoneNumber": "${contactPhoneNumber}",
        "email": "${contactEmail}"
    }`;

    post(contact);
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

    console.log(res);

}

async function get(){
    const host = window.location.host;
    const protocol = window.location.protocol;

    const url = protocol + "//" + host + "/contact_book";

    let res = await fetch(url, {
        method: "GET"
    });

    console.log(res.text());
}