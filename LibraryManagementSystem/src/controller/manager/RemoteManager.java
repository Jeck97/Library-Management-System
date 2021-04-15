package controller.manager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import controller.RequestInterface;
/**
 * 
 * This class used to get the remote from the RequestManager(Server side)
 * 
 * @author Chon Yao Jun
 *
 */
public class RemoteManager {

	/**
	 * 
	 * This method is get the registry and remote which created by RequestManager 
	 * 
	 * @return remote object
	 */
	public RequestInterface openRemote() {

		try {

			// Get registry
			Registry rmiRegistry = LocateRegistry.getRegistry(5099);

			// Look-up for the remote object
			return (RequestInterface) rmiRegistry.lookup("Request Manager");

		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
