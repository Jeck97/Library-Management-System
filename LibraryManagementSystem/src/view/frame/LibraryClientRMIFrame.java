package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import controller.RequestInterface;
import controller.manager.RemoteManager;
import controller.manager.ShutDownAdapter;
import model.Book;
import model.Borrower;

/**
 * This class represent the window for the client side RMI.
 * It send the information of client and book to the server though registry.
 * 
 * @author GroupA
 *
 */
public class LibraryClientRMIFrame extends JFrame 
				implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;

	// Private frame components
	private JComboBox<String> cmbBookName;
	private JTextField txtBorrowerName;
	private JTextField txtBorrowerContact;
	private JLabel lblBookImage;
	private JLabel lblBookAuthor;
	private JLabel lblBookDateOfPublished;
	private JLabel lblBookGenre;
	private JLabel lblBookAvailability;
	private JTextArea txtBookSynopsis;
	private JButton btnBorrow;
	private JButton btnReturn;

	// Private attributes for frame size
	private int width = 1200;
	private int height = 1050;

	// Private remote from Manager;
	private RequestInterface remoteRequest;


	/**
	 * The constructor that initialize and organize the Swing components on
	 * the frame and get the remote from server.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws RemoteException 
	 * @throws SQLException 
	 */
	public LibraryClientRMIFrame() throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException, 
	UnsupportedLookAndFeelException, RemoteException, SQLException {

		// Default frame setting
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		this.setTitle("Library Management System: Client Side");
		this.setSize(width, height);
		this.setResizable(false);
		

		// Center the frame on the screen
		this.setLocationRelativeTo(null);

		// Must close on X
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new ShutDownAdapter(this));

		// Get remote from BorrowManager from server
		RemoteManager remoteManager = new RemoteManager();
		remoteRequest = (RequestInterface) remoteManager.openRemote();

		// Organize components
		this.loadComponent();

	}


	/**
	 * This method creates and arrange Swing components for return book request
	 * 
	 * @return Swing components organized in panel
	 */
	private JPanel getReturnPanel() {

		// Get font with different sizes
		Font font20 = this.getFontStyle(20);

		// Initialize return button
		btnReturn = new JButton("RETURN");
		btnReturn.setFont(font20);
		btnReturn.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK), 
				BorderFactory.createEmptyBorder(10, 40, 10, 40)));
		btnReturn.setActionCommand("return");
		btnReturn.addActionListener(this);

		// Create a flow layout panel and add components
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
		panel.add(getInitializedLabel
				("If you want to return a book click this button.", font20));
		panel.add(btnReturn);

		return panel;

	}


	/**
	 * This method creates and arrange Swing components for the UI 
	 * of borrowing book
	 * 
	 * @return Swing components organized in panel
	 * @throws RemoteException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private JPanel getBorrowPanel() 
			throws RemoteException, ClassNotFoundException, SQLException {

		// Get font with different sizes
		Font font28 = this.getFontStyle(28);
		Font font20 = this.getFontStyle(20);

		// Define custom border
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);

		// Declare the panels that use in borrow panel
		JPanel pnlTop;
		JPanel pnlCenter;
		JPanel pnlBottom;

		// This panel is mainly divided in 3 panels
		/* Top panel contains the list of books in combo box */
		{
			// Initialize the combo box
			cmbBookName = new JComboBox<String>(remoteRequest.getBookNames());
			cmbBookName.setSelectedIndex(-1);
			cmbBookName.setFont(font20);
			cmbBookName.setBorder(blackBorder);
			cmbBookName.setPreferredSize(new Dimension(950, 
					cmbBookName.getPreferredSize().height));
			cmbBookName.setActionCommand("cmbBookName");
			cmbBookName.addItemListener(this);

			// Set auto complete to combo box
			AutoCompleteDecorator.decorate(cmbBookName);

			// Initialize and add label and combo box into top panel
			pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
			pnlTop.add(this.getInitializedLabel("Name of Book", font20));
			pnlTop.add(cmbBookName);
		} // Top panel end

		/* Center panel contains the image and information of book selected */
		{
			// Declare panels that use in center panel
			JPanel pnlCenterUpper;
			JPanel pnlCenterBottom;

			/* Upper part of center panel */
			{
				// Declare panels that use in center upper panel
				JPanel pnlLblBookImage;
				JPanel pnlBookInformation;

				/* Left part of center upper panel */
				{
					// Initialize components that will use in center upper panel
					lblBookImage = new JLabel();
					lblBookImage.setPreferredSize(new Dimension(270, 380));
					lblBookImage.setBorder(blackBorder);

					// Initialize and add label into book image panel
					pnlLblBookImage = new JPanel();
					pnlLblBookImage.add(lblBookImage);
				} // Left part of center upper panel end

				/* Right part of center upper panel */
				{
					// Initialize components that will use in center bottom panel
					lblBookAuthor = new JLabel();
					lblBookAuthor.setFont(font20);

					lblBookDateOfPublished = new JLabel();
					lblBookDateOfPublished.setFont(font20);

					lblBookAvailability = new JLabel();
					lblBookAvailability.setFont
						(new Font(Font.SANS_SERIF, Font.BOLD, 28));

					lblBookGenre = new JLabel();
					lblBookGenre.setFont(font20);

					// Initialize and add components into book information panel
					pnlBookInformation = new JPanel(new GridLayout(4,2));
					pnlBookInformation.add(this.getInitializedLabel
							("Author: ", font20));
					pnlBookInformation.add(lblBookAuthor);
					pnlBookInformation.add(this.getInitializedLabel
							("Date of Published: ", font20));
					pnlBookInformation.add(lblBookDateOfPublished);
					pnlBookInformation.add(this.getInitializedLabel
							("Genre: ", font20));
					pnlBookInformation.add(lblBookGenre);
					pnlBookInformation.add(this.getInitializedLabel
							("Availability: ", font20));
					pnlBookInformation.add(lblBookAvailability);
				} // Right part of center upper panel end

				// Initialize and add left and right parts to center upper panel
				pnlCenterUpper = new JPanel(new GridLayout(1, 2));
				pnlCenterUpper.add(pnlLblBookImage);
				pnlCenterUpper.add(pnlBookInformation);
			} // Upper part of center panel end

			/* Bottom part of center panel */
			{
				// Initialize components that will use in center bottom panel
				txtBookSynopsis = new JTextArea();
				txtBookSynopsis.setFont(font20);
				txtBookSynopsis.setEditable(false);
				txtBookSynopsis.setLineWrap(true);		
				JScrollPane scrollPane = new JScrollPane(txtBookSynopsis);

				// Initialize and add components into center bottom panel
				pnlCenterBottom = new JPanel(new BorderLayout());
				pnlCenterBottom.setBorder(new EmptyBorder(0, 20, 0, 20));
				pnlCenterBottom.add(this.getInitializedLabel
						("Synopsis:", font20), BorderLayout.NORTH);
				pnlCenterBottom.add(scrollPane);
			} // Bottom part of center panel end

			// Initialize and add upper and bottom parts to center panel
			pnlCenter = new JPanel(new GridLayout(2, 1));
			pnlCenter.add(pnlCenterUpper);
			pnlCenter.add(pnlCenterBottom);
		} // Center panel end

		/* Bottom panel contains the borrow button */
		{
			// Initialize borrow button
			btnBorrow = new JButton("Borrow");
			btnBorrow.setBorder(BorderFactory.createCompoundBorder(blackBorder,
					BorderFactory.createEmptyBorder(10, 40, 10, 40)));
			btnBorrow.setFont(font28);
			btnBorrow.setActionCommand("borrow");
			btnBorrow.addActionListener(this);

			// Initialize and borrow button into bottom panel
			pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
			pnlBottom.add(btnBorrow);
		} // Bottom panel end

		// Add top, center and bottom panels into borrow panel
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.
				createEmptyBorder(0, 20, 20, 20), blackBorder));
		panel.add(pnlTop, BorderLayout.NORTH);
		panel.add(pnlCenter, BorderLayout.CENTER);
		panel.add(pnlBottom, BorderLayout.SOUTH);

		return panel;

	}


	/**
	 * This method create a information panel for borrower to fill in their
	 * name and contact number.
	 * 
	 * @return Swing components organized in panel
	 */
	private JPanel getInformationPanel() {

		// Get font
		Font font20 = this.getFontStyle(20);

		// Create a panel to put information panel and button panel
		JPanel pnlInfoBody = new JPanel(new BorderLayout());

		// Create a information panel
		JPanel pnlInfo = new JPanel(new GridLayout(2, 2));

		// Initialize components of name and add into its flow layout panel
		txtBorrowerName = new JTextField(22);
		JPanel pnlName = new JPanel();
		pnlName.add(this.getInitializedLabel("Name   ", font20));
		pnlName.add(txtBorrowerName);

		// Initialize components of contact and add into its flow layout panel
		txtBorrowerContact = new JTextField(22);
		JPanel pnlContact = new JPanel();
		pnlContact.add(this.getInitializedLabel("Contact", font20));
		pnlContact.add(txtBorrowerContact);

		// Add the components into information panel
		pnlInfo.add(pnlName);
		pnlInfo.add(pnlContact);

		pnlInfoBody.add(pnlInfo);

		return pnlInfoBody;

	}

	/**
	 * This method create a panel that display the request number of the 
	 * according to borrower's request type
	 * 
	 * @param requestType
	 * @param number
	 * @return Swing components organized in panel
	 */
	private JPanel getRequestNoPanel(char requestType, int number) {

		// Get font 
		Font font40 = this.getFontStyle(40);
		Font font60 = this.getFontStyle(60);

		// Create a panel to put number panel
		JPanel pnlNumberDialog = new JPanel(new BorderLayout());

		// Create a number panel
		JPanel pnlText = new JPanel();
		JPanel pnlNumber = new JPanel();

		// Initialize component to be added to the panel
		if(requestType == 'B') { 

			// Add borrow label to the panel
			pnlText.add(this.getInitializedLabel
					("Your borrow number is:", font40));

			// Get and add the borrow number to the panel
			pnlNumber.add(this.getInitializedLabel
					(requestType +" "+ number, font60));

		} else if(requestType == 'R') {

			// Add return label to the panel
			pnlText.add(this.getInitializedLabel
					("Your return number is:", font40));

			// Get and add the return number to the panel
			pnlNumber.add(this.getInitializedLabel
					(requestType +" "+ number, font60));

		}

		pnlNumberDialog.add(pnlText, BorderLayout.NORTH);
		pnlNumberDialog.add(pnlNumber, BorderLayout.CENTER);

		return pnlNumberDialog;

	}


	/**
	 * This method arrange the Swing components on the frame.
	 * 
	 * @throws RemoteException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void loadComponent() 
			throws RemoteException, ClassNotFoundException, SQLException {

		// Get panels
		JPanel pnlReturn = this.getReturnPanel();
		JPanel pnlBorrow = this.getBorrowPanel();

		// Add panels to frame
		this.add(pnlReturn, BorderLayout.NORTH);
		this.add(pnlBorrow);

	}



	/**
	 * This method define a font to a generic style.
	 * 
	 * @param fontSize
	 * @return font object
	 */
	private Font getFontStyle(int fontSize) {

		return new Font (Font.SANS_SERIF, Font.PLAIN, fontSize);

	}


	/**
	 * This method return an initialized label without declare an JLabel 
	 * object at the calling function
	 * 
	 * @param text
	 * @param font
	 * @return initialized label
	 */
	private JLabel getInitializedLabel(String text, Font font) {

		// Set text and font of label
		JLabel label = new JLabel();
		label.setText(text);
		label.setFont(font);

		return label;

	}
	
	/**
	 * 
	 * This method check whether the borrower is new user
	 * Add new user or update old user if different name with same contact number
	 * 
	 * @param borrower
	 * @throws RemoteException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void validateBorrower(Borrower borrower) 
			throws RemoteException, ClassNotFoundException, SQLException {
		
		// Get record from database
		Borrower borrowerDB = remoteRequest.
				getBorrowerByContact(borrower.getContact());
		
		// Add new user
		if (borrowerDB.getId() == 0) {
			
			borrower.setId(remoteRequest.addBorrower(borrower));
			JOptionPane.showMessageDialog
				(this, "You had been register as a new borrower.");
			
		} 
		// Update user's name if different name with same contact number
		else {
			
			borrower.setId(borrowerDB.getId());
			String nameFromDB = borrowerDB.getName();
			String nameFromTxt = borrower.getName();
			if (!nameFromTxt.equalsIgnoreCase(nameFromDB)) {
				if(JOptionPane.showOptionDialog(this, "Do you want to update your name?\n" + 
						nameFromDB + " -> " + nameFromTxt, "Update Borrower Name", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						null, null, null) == JOptionPane.YES_OPTION) {
					
					// Update new name into database
					remoteRequest.updateBorrowerName(borrower);
					JOptionPane.showMessageDialog
						(this, "Your name had been updated successfully");
				} 
				else
					borrower.setName(nameFromDB);
			}
		}
	}


	@Override
	public void actionPerformed(ActionEvent event) {

		String actionCommand = event.getActionCommand();

		switch (actionCommand) {
		
		// This case is for borrow request type
		case "borrow":

			// Check whether a book is selected
			if(cmbBookName.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, "Please select a book",
						"No Book Selected", JOptionPane.WARNING_MESSAGE);

				return;
			}

			try {

				// Create new book object to add into request model
				String bookName = (String) cmbBookName.getSelectedItem();
				Book book = remoteRequest.getBookInfo(bookName);

				// Check the book availability
				if(book.isAvailability()) {

					// The flag to track the input of user's name and contact number
					boolean borrowFilled = false;
					// User's confirmation 
					int respone;

					// Loop for input (borrower name and contact)
					do {

						//Generate a JOptionDialog to get input
						respone = JOptionPane.showOptionDialog(this, this.getInformationPanel(),
								"Borrow a Book", 
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
								null, new Object[] { "Confirm", "Cancel" }, null);
						
						// Selection from user
						if(respone == 0) {
							if(!txtBorrowerName.getText().isEmpty() 
									&& !txtBorrowerContact.getText().isEmpty()) 
								borrowFilled = true;
							else 
								JOptionPane.showMessageDialog
									(this, "Name and contact must be filled");
						} else 
							break;

					} while(!borrowFilled);

					if(borrowFilled) {

						// Char store borrow request type
						char requestType = 'B';
						
						// String to store information of user
						String borrowerName = txtBorrowerName.getText();
						String borrowerContact = "6" + txtBorrowerContact.getText();

						// Create new borrower object
						Borrower borrower = new Borrower();
						
						// Set data in to object
						borrower.setName(borrowerName);
						borrower.setContact(borrowerContact);

						// Validation of borrower  
						this.validateBorrower(borrower);
							
						// Send request to the server
						int number = remoteRequest.requestHandler
								(requestType, borrower, book);

						// Display the request type and number
						JOptionPane.showOptionDialog(this, this.getRequestNoPanel
								(requestType, number), "Library Management System",
								JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE,
								null, null, null);

						// Refresh the screen
						this.getContentPane().removeAll();
						this.getContentPane().revalidate();
						this.getContentPane().repaint();
						this.loadComponent();

					}

				} else
					// Display message
					JOptionPane.showMessageDialog
						(this, "The book is unavailable at the moment.");

			} catch (RemoteException | SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;


		case "return":

			// The flag to track the input of user's name and contact number
			boolean returnFilled = false;

			// Loop for input (borrower name and contact)
			do {

				//Generate a JOptionDialog
				int returnResult = JOptionPane.showOptionDialog(this, 
						this.getInformationPanel(), "Library Management System",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, new Object[] { "Confirm", "Cancel" }, null);
				
				// Get response from user
				if(returnResult == 0) {
					if(!txtBorrowerName.getText().isEmpty() 
							&& !txtBorrowerContact.getText().isEmpty())
						returnFilled = true;
					else
						JOptionPane.showMessageDialog(this, 
								"Name and contact must be filled", "Error", 1 , null);

				} else
					break;

			} while(!returnFilled);

			// Update borrower_return table in database
			if(returnFilled) {

				try {
					// Char store borrow request type
					char requestType = 'R';
					
					// String to store user's information
					String borrowerName = txtBorrowerName.getText();
					String borrowerContact = "6" + txtBorrowerContact.getText();

					// Create new borrower object
					Borrower borrower = new Borrower();
					
					// Insert data into object
					borrower.setName(borrowerName);
					borrower.setContact(borrowerContact);

					// Check the duplication of borrower
					if (remoteRequest.isDuplicateReturn(borrower)) {
						JOptionPane.showMessageDialog(this, "You are already in the queue");
						return;
					}
						
					// The flag represents whether the borrower exists
					boolean found = remoteRequest.isAnyBookReturn(borrowerContact);

					if(found) {
						// Validation of borrower
						this.validateBorrower(borrower);
						
						// Send request to the server
						int number = remoteRequest.requestHandler
								(requestType, borrower, new Book());

						// Display the queue number
						JOptionPane.showOptionDialog(this, this.getRequestNoPanel
								(requestType, number), "Library Management System",
								JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE,
								null, null, null);
					} else
						JOptionPane.showMessageDialog(this, "You have no book to be return.");

				} catch (RemoteException | SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;

		} // end of switch-actionCommand

	}

	/**
	 * 
	 * This method manages the list of the combo box to display
	 * the book name
	 * 
	 * @param event
	 * 
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {

		try {

			// Display information of book
			Book book = remoteRequest.getBookInfo((String)cmbBookName.getSelectedItem());
			
			// Insert data
			lblBookAuthor.setText(book.getAuthor());
			lblBookDateOfPublished.setText(book.getDatePublished().toString());
			lblBookGenre.setText(book.getGenre());
			
			if(book.isAvailability()) {
				lblBookAvailability.setText("AVAILABLE");
				lblBookAvailability.setForeground(new Color(0, 204, 0));
			} else {
				lblBookAvailability.setText("NOT AVAILABLE");
				lblBookAvailability.setForeground(Color.RED);
			}

			txtBookSynopsis.setText(book.getSynopsis());
			lblBookImage.setIcon(new ImageIcon("image/"+book.getBookName()+".jpg"));

		} catch (RemoteException | SQLException | ClassNotFoundException e) {

			e.printStackTrace();

		}

	}

}
