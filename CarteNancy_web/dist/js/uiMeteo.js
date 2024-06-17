import { getDataTemp } from "./meteo.js";

export async function displayMeteo() {
    let data = await getDataTemp();
    uiMeteo(data);
}

function uiMeteo(listeMeteo) {
    let node = document.createElement("div");
    document.querySelector("#content").appendChild(node);
    node.setAttribute("id", "meteo");

    const toStringMap = element => `
        <div class="meteo-bubble">
            <img class="meteo-icon" src="${element.icon}" alt="weather icon"/>
            <div class="meteo-info">
                <p class="meteo-date">${element.jour}</p>
                <p class="meteo-time">${element.heure}</p>
                <p class="meteo-temperature">Température: ${element.temperature}</p>
                <p class="meteo-pluie">Pluie: ${element.pluie}</p>
                <p class="meteo-vent">Vent: ${element.vent}</p>
                <p class="meteo-nebulosite">Nébulosité: ${element.nebulosite}</p>
            </div>
        </div>
    `;
    let tabString = listeMeteo.map(toStringMap);
    const concatReduce = (acc, element) => acc + element;
    let result = tabString.reduce(concatReduce, "");
    node.innerHTML = result;
}
