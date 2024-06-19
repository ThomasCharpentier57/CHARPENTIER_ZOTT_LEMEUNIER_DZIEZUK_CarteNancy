import { getAllRestaurant } from "./restaurant.js";
import { displayMeteo } from "./uiMeteo.js";
import { getCirculationIncidents } from "./traffics.js";
import { getCollegesLycees } from './collegesLycees.js';

const map = L.map('map').setView([48.692054, 6.184417], 13);

const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const velibLayer = L.layerGroup().addTo(map);
const restaurantLayer = L.layerGroup().addTo(map);
const trafficLayer = L.layerGroup().addTo(map);
const collegesLyceesLayer = L.layerGroup().addTo(map);

const overlays = {
    "Vélos": velibLayer,
    "Restaurants": restaurantLayer,
    "Traffic": trafficLayer,
    "Collèges et Lycées": collegesLyceesLayer
};

L.control.layers(null, overlays).addTo(map);

async function loadStations() {
    const [infoResponse, statusResponse] = await Promise.all([
        fetch('https://transport.data.gouv.fr/gbfs/nancy/station_information.json'),
        fetch('https://transport.data.gouv.fr/gbfs/nancy/station_status.json')
    ]);

    const [infoData, statusData] = await Promise.all([
        infoResponse.json(),
        statusResponse.json()
    ]);

    const stations = infoData.data.stations;
    const statuses = statusData.data.stations;

    const statusMap = new Map();
    statuses.forEach(status => {
        statusMap.set(status.station_id, status);
    });

    return stations.map(station => ({
        ...station,
        status: statusMap.get(station.station_id)
    }));
}

function addStationsToMap(stations) {
    stations.forEach(station => {
        const popupContent = `
            <b>${station.name}</b><br>
            ${station.address}<br>
            <b>Vélos disponibles:</b> ${station.status.num_bikes_available}<br>
            <b>Places disponibles:</b> ${station.status.num_docks_available}
        `;
        L.marker([station.lat, station.lon])
            .addTo(velibLayer)
            .bindPopup(popupContent);
    });

}

loadStations().then(stations => {
    addStationsToMap(stations);
});

const greenIcon = new L.Icon({
    iconUrl: './dist/img/ping_green.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

function addRestaurantToMap() {
    getAllRestaurant().then(restaurants => {
        restaurants.forEach(restaurant => {
            document.getElementById("allRestau").innerHTML += `<option value="${restaurant.id}">${restaurant.name}</option>`;

            let popupContent = `
                <b>${restaurant.name}</b><br>
                ${restaurant.adresse}<br>
            `;
            L.marker([restaurant.lat, restaurant.lont], {icon: greenIcon})
                .addTo(restaurantLayer)
                .bindPopup(popupContent);
        });
    });


}

const orangeIcon = new L.Icon({
    iconUrl: './dist/img/ping_orange.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

function addTrafficToMap() {
    getCirculationIncidents().then(incidents => {
        incidents.forEach(incident => {
            const popupContent = `
                <b>${incident.short_description}</b><br>
                ${incident.description}<br>
                <b>Lieu:</b> ${incident.location}<br>
                <b>Code postal:</b> ${incident.postcode}<br>
                <b>Ville:</b> ${incident.city}<br>
                <b>Début:</b> ${incident.start}<br>
                <b>Fin:</b> ${incident.end}
            `;
            L.marker([incident.lat, incident.lon], {icon: orangeIcon})
                .addTo(trafficLayer)
                .bindPopup(popupContent);
        });
    });
}

const redIcon = new L.Icon({
    iconUrl: './dist/img/ping_red.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

function addCollegesLyceesToMap() {
    getCollegesLycees().then(collegesLycees => {
        if (collegesLycees) {
            collegesLycees.forEach(etablissement => {
                const popupContent = `
                    <b>${etablissement.appellation_officielle}</b><br>
                    ${etablissement.adresse_uai}, ${etablissement.code_postal_uai} ${etablissement.localite_acheminement_uai}<br>
                    <b>Type:</b> ${etablissement.nature_uai_libe}<br>
                    <b>Secteur:</b> ${etablissement.secteur_public_prive_libe}<br>
                    <b>Ouvert depuis:</b> ${etablissement.date_ouverture}
                `;
                L.marker([etablissement.latitude, etablissement.longitude], {icon: redIcon})
                    .addTo(collegesLyceesLayer)
                    .bindPopup(popupContent);
            });
        }
    });
}

addRestaurantToMap();
displayMeteo();
addTrafficToMap();
addCollegesLyceesToMap();

map.on('click', function(e) {
    const { lat, lng } = e.latlng;
    const popupContent = `
        <div class="popup-form">
            <h3>Add Restaurant</h3>
            <form id="restaurantForm">
                <label for="nomRestau">Name:</label>
                <input type="text" id="nomRestau" name="nomRestau" required><br>
                <label for="adresseRestau">Address:</label>
                <input type="text" id="adresseRestau" name="adresseRestau" required><br>
                <button type="submit">Add Restaurant</button>
            </form>
        </div>
    `;

    const popup = L.popup()
        .setLatLng([lat, lng])
        .setContent(popupContent)
        .openOn(map);

    document.getElementById('restaurantForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const nomRestau = event.target.nomRestau.value;
        const adresseRestau = event.target.adresseRestau.value;

        addRestaurant({
            nomRestau,
            adresseRestau,
            latitudeRestau: lat,
            longitudeRestau: lng
        });

        map.closePopup();
    });
});

function addRestaurant(restaurant) {
    const popupContent = `
        <b>${restaurant.nomRestau}</b><br>
        ${restaurant.adresseRestau}<br>
    `;
    L.marker([restaurant.latitudeRestau, restaurant.longitudeRestau], {icon: greenIcon})
        .addTo(restaurantLayer)
        .bindPopup(popupContent);
}