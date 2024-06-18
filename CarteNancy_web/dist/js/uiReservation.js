// uiReservation.js

class UIReservation {
    constructor() {
        this.currentRestaurant = null;
        this.currentMarker = null;
    }

    submitForm() {
        const nom = document.getElementById('nom').value;
        const prenom = document.getElementById('prenom').value;
        const nbConvives = document.getElementById('nbConvives').value;
        const telephone = document.getElementById('telephone').value;

        this.currentRestaurant.reserver(nom, prenom, nbConvives, telephone);
    }

    showReservationForm(restaurant, marker) {
        const oldForm = document.getElementById("formulaireReservation");
        if (oldForm) {
            oldForm.remove();
        }

        this.currentRestaurant = restaurant;
        this.currentMarker = marker;

        const node = document.createElement("div");
        document.querySelector("#affichageMap").appendChild(node);
        node.classList.add("formReservation");
        node.id = "formulaireReservation";

        const html = `
            <div id="informationRestoForm">
                <h2>${restaurant.nomRestau}</h2>
                <p>${restaurant.adresseRestau}</p>
            </div>
            <form id="reservationForm">
                <h3>Réserver une table :</h3>
                <div id="formulaireNom">
                    <label for="nom">Nom</label>
                    <input type="text" id="nom" required><br>
                </div>
                <div id="formulairePrenom">
                    <label for="prenom">Prénom</label>
                    <input type="text" id="prenom" required><br>
                </div>
                <div id="formulaireNbConv">
                    <label for="nbConvives">Nombre de convives</label>
                    <input type="number" id="nbConvives" required min="1"><br>
                </div>
                <div id="formulaireTelephone">
                    <label for="telephone">Téléphone</label>
                    <input type="tel" id="telephone" required><br>
                </div>
                <button type="button" id="submit"><p>Réserver</p></button>
            </form>`;

        node.innerHTML = html;

        setTimeout(() => {
            node.classList.add("show");
        }, 10);

        document.addEventListener("click", (event) => {
            const isClickedInsideNode = node.contains(event.target);
            if (!isClickedInsideNode && event.target !== marker._icon) {
                this.currentMarker = null;
                node.classList.remove("show");
                setTimeout(() => {
                    node.remove();
                }, 500);
            }
        });

        document.getElementById("submit").addEventListener("click", () => this.submitForm());
    }
}

export { UIReservation };
