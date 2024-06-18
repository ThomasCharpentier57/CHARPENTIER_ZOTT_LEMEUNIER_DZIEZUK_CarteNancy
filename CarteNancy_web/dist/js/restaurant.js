class Restaurant {
    constructor(idR, nomR, adrR, latR, longR) {
        this.idRestau = idR;
        this.nomRestau = nomR;
        this.adresseRestau = adrR;
        this.latitudeRestau = latR;
        this.longitudeRestau = longR;
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
