export async function getCollegesLycees() {
    try {
        const response = await fetch('https://data.education.gouv.fr/api/explore/v2.1/catalog/datasets/fr-en-adresse-et-geolocalisation-etablissements-premier-et-second-degre/records?where=(libelle_commune=%27Nancy%27%20OR%20libelle_commune=%27Laxou%27%20OR%20libelle_commune=%27Tomblaine%27%20OR%20libelle_commune=%27Essey-l%C3%A8s-Nancy%27%20OR%20libelle_commune=%27Villers-l%C3%A8s-Nancy%27%20OR%20libelle_commune=%27Vand%C5%93uvre-l%C3%A8s-Nancy%27)%20AND%20(nature_uai_libe=%27COLLEGE%27%20OR%20nature_uai_libe=%27LYCEE%20GENERAL%20ET%20TECHNOLOGIQUE%27%20OR%20nature_uai_libe=%27LYCEE%20PROFESSIONNEL%27)');
        const data = await response.json();
        const collegesLycees = data.results.map(record => ({
            numero_uai: record.numero_uai,
            appellation_officielle: record.appellation_officielle,
            denomination_principale: record.denomination_principale,
            patronyme_uai: record.patronyme_uai,
            secteur_public_prive_libe: record.secteur_public_prive_libe,
            adresse_uai: record.adresse_uai,
            boite_postale_uai: record.boite_postale_uai,
            code_postal_uai: record.code_postal_uai,
            localite_acheminement_uai: record.localite_acheminement_uai,
            libelle_commune: record.libelle_commune,
            latitude: record.position.lat,
            longitude: record.position.lon,
            nature_uai_libe: record.nature_uai_libe,
            etat_etablissement_libe: record.etat_etablissement_libe,
            libelle_departement: record.libelle_departement,
            libelle_region: record.libelle_region,
            libelle_academie: record.libelle_academie,
            date_ouverture: record.date_ouverture
        }));

        return collegesLycees;
    } catch (error) {
        console.error('Error fetching colleges and lycees data:', error);
        return null;
    }
}