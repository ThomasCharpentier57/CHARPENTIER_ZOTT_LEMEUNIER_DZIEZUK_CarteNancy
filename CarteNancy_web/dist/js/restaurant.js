class Restaurant {
    constructor(idR, nomR, adrR, latR, longR) {
        this.idRestau = idR;
        this.nomRestau = nomR;
        this.adresseRestau = adrR;
        this.latitudeRestau = latR;
        this.longitudeRestau = longR;
    }

    reserver(nom, prenom, nbConvives, telephone) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8000/applications/restaurantRes", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify({
            id: this.idRestau,
            nom: nom,
            prenom: prenom,
            nbConvives: nbConvives,
            telephone: telephone
        }));
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                const formulaire = document.getElementsByClassName("formReservation")[0];
                formulaire.innerHTML = `<p>${response.message}</p>`;
                setTimeout(() => {
                    formulaire.remove();
                }, 3000);
            }
        };
    }
}

export const getAllRestaurant = async function () {
    try {
        const response = await fetch("http://localhost");
        const data = await response.json();
        let restaurants = data.map(restaurant => new Restaurant(
            restaurant.idRestau, restaurant.nomRestau, restaurant.adresseRestau,
            restaurant.latitudeRestau, restaurant.longitudeRestau
        ));
        return restaurants;
    } catch (error) {
        console.log(error);
    }
}

export { Restaurant };
