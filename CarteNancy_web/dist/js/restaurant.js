class Restaurant {
    constructor(idR, nomR, adrR, latR, longR) {
        this.idRestau = idR;
        this.nomRestau = nomR;
        this.adresseRestau = adrR;
        this.latitudeRestau = latR;
        this.longitudeRestau = longR;
    }
}


export const getAllRestaurant = function () {
    fetch("http://localhost").then(
        function (response) {
            return response.json();
        }
    ).then(
        function (data) {
            let restaurants = [];
            for (let i = 0; i < data.length; i++) {
                let restaurant = new Restaurant(data[i].idRestau, data[i].nomRestau, data[i].adresseRestau, data[i].latitudeRestau, data[i].longitudeRestau);
                restaurants.push(restaurant);
            }
            return restaurants;
        }
    ).catch(
        function (error) {
            console.log(error);
        }
    )
}
