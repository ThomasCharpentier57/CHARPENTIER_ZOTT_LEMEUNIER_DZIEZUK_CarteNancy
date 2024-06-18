import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GestionBDD
 */
public class ServiceRestaurant implements ServiceRestaurantInterface {
    private Connection connection;

    public ServiceRestaurant(String user, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");            
            System.out.println("Driver loaded");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:INFODB", user, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Erreur de connexion" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found" + e);
        } catch (Exception e) {
            System.out.println("Erreur" + e);
        }
    }

    @Override
    public String getCoordRestaurant() throws RemoteException {
        StringBuilder sb = new StringBuilder("{\n");
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM restaurant");
            sb.append("\t\"restaurants\": [\n");
            while (rs.next()) {
                sb.append("\t{\n");
                sb.append("\t\t\"name\": " + rs.getString(1) + ",\n");
                sb.append("\t\t\"adress\": " + rs.getString(2) + ",\n");
                sb.append("\t\t\"longitude\": " + rs.getString(3) + ",\n");
                sb.append("\t\t\"latitude\": " + rs.getString(4) + ",\n");
                sb.append("\t},\n");
            }
            sb.append("\t]\n");
            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        sb.append("}");
        return sb.toString();


    }

    @Override
    public void reserverTable(String idRestau, String nom, String prenom, String nbPersonnes, String nTelephone) throws RemoteException {
        try {

            int table_disponible = verif_table(idRestau, nbPersonnes);

            if (table_disponible == -1) {
                System.out.println("Pas de table disponible");
                return;
            }

            PreparedStatement transaction = this.connection.prepareStatement(
                    "LOCK TABLE reservation IN SHARE ROW EXCLUSIVE MODE"
            );

            transaction.executeUpdate();

            PreparedStatement reserver_table = this.connection.prepareStatement(
                    "INSERT INTO reservation (idReservation, idRestaurant, numtab, nom, prenom, nbpers, numTelephone, dateReservation) VALUES (?, ?, ?, ?, ?,?, ?, to_date(?,'YYYY-MM-DD'))"
            );

            PreparedStatement idMax = this.connection.prepareStatement(
                    "SELECT MAX(idReservation) FROM reservation"
            );

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedNow = now.format(formatter);

            ResultSet resultat_idMax = idMax.executeQuery();
            resultat_idMax.next();
            int idReservation = resultat_idMax.getInt(1) + 1;

            reserver_table.setInt(1, idReservation);
            reserver_table.setString(2, idRestau);
            reserver_table.setInt(3, table_disponible);
            reserver_table.setString(4, nom);
            reserver_table.setString(5, prenom);
            reserver_table.setString(6, nbPersonnes);
            reserver_table.setString(7, nTelephone);
            reserver_table.setString(8, formattedNow);

            reserver_table.executeUpdate();

            this.connection.commit();

            PreparedStatement t = this.connection.prepareStatement(
                    "SELECT * FROM RESERVATION"
            );

            ResultSet taa = t.executeQuery();

            while (taa.next()){
                System.out.println(taa.getNString(1) + taa.getString(2) + "\n");
            }

            this.connection.close();

            System.out.println("RESERVER");

        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erreur lors de la réservation : " + e.getMessage());
        }
    }

    private int verif_table(String idRestau, String nbPlace) throws SQLException {

        List<Integer> list_table = new ArrayList<>();

        PreparedStatement verif_table = this.connection.prepareStatement(
                "SELECT numtab FROM tabl WHERE idRestaurant = ? AND nbplace >= ?"
        );

        verif_table.setString(1, idRestau);
        verif_table.setString(2, nbPlace);

        ResultSet resultat_tabl_dispo = verif_table.executeQuery();

        while (resultat_tabl_dispo.next()) {
            list_table.add(resultat_tabl_dispo.getInt(1));
            System.out.println("Table dispo : " + resultat_tabl_dispo.getInt(1));
            System.out.println(list_table);
        }

        PreparedStatement verif_restau = this.connection.prepareStatement(
                "SELECT numtab FROM reservation WHERE numtab = ? AND dateReservation != to_date(?,'YYYY-MM-DD')"
        );

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedNow = now.format(formatter);

        System.out.println(formattedNow);

        int table_dispo = -1;

        for (int j : list_table) {
            try {
                System.out.println("Vérification pour table : " + j);

                verif_restau.setInt(1, j);
                verif_restau.setString(2, formattedNow);

                ResultSet res = verif_restau.executeQuery();

                // Vérifier si la table est disponible à la date spécifiée
                if (res.next()) {
                    table_dispo = j; // Table disponible trouvée
                    System.out.println("Table sélectionnée : " + table_dispo);
                    break; // Sortir de la boucle dès qu'une table est trouvée
                }

                res.close();
            } catch (SQLException q) {
                System.out.println("Erreur de vérification de la table : " + q.getMessage());
            }
        }

        return table_dispo;
    }
}