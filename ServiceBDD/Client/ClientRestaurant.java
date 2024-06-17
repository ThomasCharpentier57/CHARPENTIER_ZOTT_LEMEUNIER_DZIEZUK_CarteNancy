import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.text.Style;

import Service.ServiceRestaurantInterface;

public class ClientRestaurant {

    final static String service = "ServiceRestaurant";
    static int port = 0;
    static String host = "localhost";

    public static void main(String[] args) throws RemoteException, NotBoundException {

        if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else if (args.length == 1) {
            host = args[0];
        } else if (args.length == 0) {
            System.out.println("Utilisation de : host = localhost, port = 0");
        } else {
            System.out.println("Nombre de paramètres incorrects (0, 1 ou 2)");
            System.exit(1);
        }

        ServiceRestaurantInterface serviceRestaurant = (ServiceRestaurantInterface) LocateRegistry.getRegistry(host, port).lookup(service);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue");
        System.out.println("0 : Quitter");

        int choice;
        while (true) {

            System.out.println("Quelle fonction voulez-vous utiliser (1-> Coordonnées d'un restaurant, 2-> Réserver table) : ");
            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Fin du client restaurant");
                scanner.close();
                System.exit(0);
                    break;
                case 1:
                    System.out.println(serviceRestaurant.getCoordRestaurant());
                    break;
                case 2:
                    System.out.println("Veuillez entrer l'id du restaurant");
                    Scanner scanner_idRestau = new Scanner(System.in);
                    int idRestau = scanner_idRestau.nextInt();

                    System.out.println("Veuillez entrer le nom");
                    Scanner scanner_nom = new Scanner(System.in);
                    String nom = scanner_nom.nextLine();

                    System.out.println("Veuillez entrer le prénom");
                    Scanner scanner_prenom = new Scanner(System.in);
                    String prenom = scanner_prenom.nextLine();

                    System.out.println("Veuillez entrer le nombre de personnes");
                    Scanner scanner_nbPersonne = new Scanner(System.in);
                    int nbPersonnes = scanner_nbPersonne.nextInt();

                    System.out.println("Veuillez entrer le numéro de téléphone");
                    Scanner scanner_numTel = new Scanner(System.in);
                    int numTel = scanner_numTel.nextInt();    

                    reserverTable(idRestau,nom,prenom,nbPersonnes,numTel);
                    break;           
                default:
                break;
            }    

            
        }


    }
}
