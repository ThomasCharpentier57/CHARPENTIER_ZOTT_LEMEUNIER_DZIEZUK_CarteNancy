

export const reserver = async function (idRestau, nom, prenom, nbConvives, telephone) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8000/applications/restaurantRes", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({
        id: idRestau,
        nom: nom,
        prenom: prenom,
        nbConvives: nbConvives,
        telephone: telephone
    }));
    
    sxhr.onreadystatechange = () => {
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


export const getAllRestaurant = async function () {
    try {
        const response = await fetch("http://localhost:8000/applications/restaurantCoor");
        const data = await response.json();
        let restaurants = [];

        console.log(data);

        for (const resto of data) {
            restaurants.push({
                id: resto.id,
                name: resto.name,
                adresse: resto.adress,
                lont: resto.latitude,
                lat: resto.longitude
            });
        }
        return restaurants;
    } catch (error) {
        console.log(error);
    }
}