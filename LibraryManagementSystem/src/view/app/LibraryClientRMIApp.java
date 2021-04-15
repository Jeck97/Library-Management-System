package view.app;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.UnsupportedLookAndFeelException;

import view.frame.LibraryClientRMIFrame;

/**
 * 
 * This class opens the frame of application
 * 
 * @author Chon Yao Jun
 *
 */
public class LibraryClientRMIApp implements Serializable{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, 
			IllegalAccessException, RemoteException,
			UnsupportedLookAndFeelException, SQLException {

		// Launch the client frame
		LibraryClientRMIFrame clientFrame = new LibraryClientRMIFrame();
		clientFrame.setVisible(true);

	}

}
