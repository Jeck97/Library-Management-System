package controller.manager;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import view.frame.LibraryServerRMIFrame;

/**
 * 
 * This class ensures the no request before closing program
 * 
 * @author Chon Yao Jun
 *
 */
public class ShutDownAdapter extends WindowAdapter {

	// The string contains the password
	private static final String PASSWORD = "abc123";

	private JFrame frame;

	// Set the frame
	public ShutDownAdapter(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * 
	 * This method will be called before window closing
	 * 
	 * @param event
	 * 
	 */
	@Override
	public void windowClosing(WindowEvent e) {

		// Compare the class of frame
		if (frame.getClass().equals(LibraryServerRMIFrame.class)) {
			LibraryServerRMIFrame serverFrame = (LibraryServerRMIFrame) frame;
			
			// Check whether the requests clear
			if (!serverFrame.isRequestEmpty()) {
				JOptionPane.showMessageDialog(frame, "There are still unresolved requests.\n"
						+ "Please resolve them before shuting down the system.", 
						"Shut Down", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		// Password field for password
		JPasswordField txtPassword = new JPasswordField();

		// Panel for input password
		JPanel pnlPassword = new JPanel(new BorderLayout());
		pnlPassword.add(new JLabel("Please enter the password: "), BorderLayout.NORTH);
		pnlPassword.add(txtPassword);

		// Loop for input password
		while (JOptionPane.showOptionDialog(frame, pnlPassword, "System Shut Down", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, 
				null, new Object[] { "Confirm", "Cancel" }, null) == JOptionPane.YES_OPTION) {

			if (PASSWORD.equals(new String(txtPassword.getPassword()))) {
				frame.dispose();
				break;
			} else {
				JOptionPane.showMessageDialog(frame, "Wrong Password!",
						"Invalid Password", JOptionPane.WARNING_MESSAGE, null);
				txtPassword.setText(null);
			}
		}

	}

}
