package view.app;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import controller.RequestInterface;
import controller.manager.RequestManager;
import view.frame.LibraryServerRMIFrame;

public class LibraryServerRMIApp implements Serializable{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		try {

			// Create interface object
			RequestInterface requestManager = new RequestManager();

			// Get registry
			Registry rmiRegistry = LocateRegistry.createRegistry(5099);

			// Register interface object as remote object
			rmiRegistry.rebind("Request Manager", requestManager);

			// Launch the server frame
			LibraryServerRMIFrame serverFrame = 
					new LibraryServerRMIFrame(requestManager);
			serverFrame.setVisible(true);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
