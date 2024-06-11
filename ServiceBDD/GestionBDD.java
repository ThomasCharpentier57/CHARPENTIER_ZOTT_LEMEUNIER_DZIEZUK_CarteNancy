import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * GestionBDD
 */
public class GestionBDD implements ServiceBDD {
    private Connection connection;

    public GestionBDD(String user, String password){
        try{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb", user, password);
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
    public void reserverTable(String nom, String prenom, int nbPersonnes, String nTelephone) throws RemoteException {
        try{
            PreparedStatement sPreparedStatement = connection.prepareStatement("INSERT INTO RESERVERVATION VALUES ?,?,?");
            sPreparedStatement.setString(0, nom);
            sPreparedStatement.setString(1, prenom);
            sPreparedStatement.setInt(2, nbPersonnes);
            sPreparedStatement.setString(3, nTelephone);
            sPreparedStatement.executeQuery();  
        } catch(SQLException e ){
            System.out.println(e.getSQLState());
        }
    }

    
    
}