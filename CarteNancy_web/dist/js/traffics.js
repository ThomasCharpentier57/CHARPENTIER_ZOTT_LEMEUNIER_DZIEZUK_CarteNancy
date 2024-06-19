export async function getCirculationIncidents() {
    try {
        let response = await fetch('http://localhost:8000/applications/incident');
        response = await response.json();


        let incidents = [];

        for (const incident of response.incidents) {

            let [lat, lon] = incident.location.polyline.split(' ');
            
            let incidentLocation = await fetch(`https://api-adresse.data.gouv.fr/reverse?lon=${lon}&lat=${lat}&type=street&limit=1`)
                .then(r => r.json())
                .then(r => r.features[0].properties)
                .catch(e => console.error(e));

            incidents.push({
                description: incident.description,
                short_description: incident.short_description,
                lat: parseFloat(lat),
                lon: parseFloat(lon),
                location: incidentLocation.name || "N/A",
                postcode: incidentLocation.postcode || "N/A",
                city: incidentLocation.city || "N/A",
                start: convertDateToFrench(new Date(incident.starttime)),
                end: convertDateToFrench(new Date(incident.endtime)),
            });
        }

        return incidents;

    } catch (error) {
        console.error('Error fetching circulation incidents:', error);
        return null;
    }
}

function convertDateToFrench(date) {
    const options = { year: 'numeric', month: 'numeric', day: 'numeric' };
    return date.toLocaleDateString('fr-FR', options);
}
