package Client;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Service.ServiceBDD;

/**
 * GestionBDD
 */
public class GestionBDD implements ServiceBDD {
    private Connection connection;

    public GestionBDD(String user, String password){
        try{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb", user, password);
        connection.setAutoCommit(false);
        }catch(SQLException e ){
            System.out.println("Erreur de connexion");
        }
    }

    @Override
    public String getCoordRestaurant() throws RemoteException {
        StringBuilder sb = new StringBuilder("[\n");
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Restaurant;");
            while(rs.next()){
                sb.append("\t{\n");
                sb.append("\t\t\"name\": "+rs.getString(0)+",\n");
                sb.append("\t\t\"adress\": "+rs.getString(1)+",\n");
                sb.append("\t\t\"name\": "+rs.getString(3)+",\n");
                sb.append("\t},\n");
            }
            rs.close();
            statement.close();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sb.append("]");
        return sb.toString();
    } 

    @Override
    public void reserverTable(int idRestau, String nom, String prenom, int nbPersonnes, String nTelephone) throws RemoteException {
        try {

            int table_disponible = verif_table(idRestau, nbPersonnes);

            if(table_disponible == -1){
                System.out.println("Pas de table disponible");
                return;
            }

            PreparedStatement transaction = this.connection.prepareStatement(
                    "LOCK TABLE reservation IN SHARE ROW EXCLUSIVE MODE"
            );

            transaction.executeUpdate();

            //ICI A FINIR
            //Pour ajouter une reservation il faut recuperer l'id max de la table reservation et ajouter 1
            //Il faut aussi ajouter la date de la reservation (ajourd'hui)

            PreparedStatement reserver_table = this.connection.prepareStatement(
                "INSERT INTO reservation (idReservation, idRestaurant, numtab, nom, prenom, nbpers, numTelephone, dateReservation) VALUES (?, ?, ?, ?, ?, ?, to_date(?,'dd/mm/yyyy'))"
            );

            reserver_table.setInt(1, );
            reserver_table.setInt(2, );
            reserver_table.setInt(3, );
            reserver_table.setInt(4, );
            reserver_table.setInt(5, );
            reserver_table.setInt(6, );
            reserver_table.setInt(7, );

            this.connection.commit();
            this.connection.close();

        } catch (SQLException e) {
            System.out.println(e);
            //this.connection.rollback();
        } 
    }
    
    private int verif_table(int idRestau, int nbPlace) throws SQLException {

        int[] list_table = {};

        PreparedStatement verif_table = this.connection.prepareStatement(
            "SELECT numtab FROM tabl WHERE idRestaurant = ? AND nbplace >= ?"
        );

        verif_table.setInt(idRestau, 1);
        verif_table.setInt(nbPlace, 2);

        ResultSet resultat_tabl_dispo = verif_table.executeQuery();

        if (resultat_tabl_dispo.next() != false) {

            int i = 0;

            while (resultat_tabl_dispo.next()) {
                list_table[i] = resultat_tabl_dispo.getInt(1);
                System.out.println(resultat_tabl_dispo.getInt(1));
                i++;
            }

            PreparedStatement verif_restau = this.connection.prepareStatement(
            "SELECT numtab FROM reservation WHERE numtab = ? AND dateReservation = to_date(?,'dd/mm/yyyy')"
            );

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedNow = now.format(formatter);

            ResultSet resultat;

            int table_dispo = -1;

            for (int j : list_table) {

                verif_restau.setInt(1, j);
                verif_restau.setString(2, formattedNow);

                resultat = verif_restau.executeQuery();

                resultat.next();
                table_dispo = resultat.getInt(1);
            }

            return table_dispo;

        } else {
            return -1;
        }
    }

    
    
}