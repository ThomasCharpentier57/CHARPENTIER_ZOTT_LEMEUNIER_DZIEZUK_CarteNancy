import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerService{
    public static void main(String[] args) throws RemoteException {
        ServiceTravaux serviceProxyBlocage = new ServiceTravaux();
        Remote rd = UnicastRemoteObject.exportObject(serviceProxyBlocage, 0);

        Registry reg = LocateRegistry.getRegistry("localhost");
        reg.rebind("ServiceTravaux", rd);
    }
}