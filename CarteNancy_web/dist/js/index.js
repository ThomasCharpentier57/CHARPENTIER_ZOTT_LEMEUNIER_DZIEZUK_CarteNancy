const map = L.map('map').setView([48.692054, 6.184417], 13);

const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

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
            .addTo(map)
            .bindPopup(popupContent);
    });
}

loadStations().then(stations => {
    addStationsToMap(stations);
});