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
    static int port = 6789;
    static String host = "localhost";

    public static void main(String[] args) throws RemoteException, NotBoundException {

        if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else if (args.length == 1) {
            host = args[0];
        } else if (args.length == 0) {
            System.out.println("Using default values: host = localhost, port = 10002");
        } else {
            System.out.println("Usage: java -jar ClientRestaurant.jar [host] [port]");
            System.exit(1);
        }

        ServiceRestaurantInterface serviceRestaurant = (ServiceRestaurantInterface) LocateRegistry.getRegistry(host, port).lookup(service);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans le client restaurant");
        System.out.println("Example pour faire une reservation : ");
        System.out.println("0. Quitter");

        int choice;
        while (true) {

            System.out.println("Quelle fonction voulez-vous utiliser ?");
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

                    System.out.println("Veuillez entrer le nom");
                    System.out.println("Veuillez entrer le prénom");
                    System.out.println("Veuillez entrer le nombre de personnes");
                    System.out.println("Veuillez entrer le numéro de téléphone");

                    reserverTable(serviceRestaurant, scanner);
                    break;           
                default:
                break;
            }    

            // Demande des paramètres
            Object[] params = new Object[method.getParameterCount()];
            for (int j = 0; j < method.getParameterCount(); j++) {
                Parameter parameter = method.getParameters()[j];
                System.out.println("Veuillez entrer le paramètre " + (j + 1) + " " + parameter.getName() + " de type " + parameter.getType().getSimpleName());
                if (method.getParameterTypes()[j] == int.class) {
                    params[j] = scanner.nextInt();
                } else if (method.getParameterTypes()[j] == String.class) {
                    params[j] = scanner.next();
                }
            }

            // Appel de la méthode
            try {
                Object result = method.invoke(serviceRestaurant, params);
                System.out.println("Résultat: " + result);
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }


    }
}
