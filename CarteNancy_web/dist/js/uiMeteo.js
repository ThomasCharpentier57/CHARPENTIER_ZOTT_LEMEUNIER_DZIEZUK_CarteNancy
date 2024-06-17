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
        <div id="${element.date}">
            <p id="elemJour">${element.jour}</p>
            <p id="elemHeure">${element.heure}</p>
            <img src="${element.icon}"/>
            <div id="temperature">${element.temperature}</div>
        </div>
    `;
    let tabString = listeMeteo.map(toStringMap);
    const concatReduce = (acc, element) => acc + element;
    let result = tabString.reduce(concatReduce, "");
    node.innerHTML = result;
}
