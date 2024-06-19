// uiReservation.js
import {reserver} from "./restaurant";

function submitForm() {

    window.alert("Restaurant réserver");

    const id = document.getElementById('id').value;
    const nom = document.getElementById('nom').value;
    const prenom = document.getElementById('prenom').value;
    const nbConvives = document.getElementById('nbConvives').value;
    const telephone = document.getElementById('telephone').value;

    reserver(id, nom, prenom, nbConvives, telephone);
}

export {submitForm}