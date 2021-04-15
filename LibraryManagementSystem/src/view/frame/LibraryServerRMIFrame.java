package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.RequestInterface;
import controller.manager.RequestManager;
import controller.manager.ShutDownAdapter;
import model.Book;
import model.Request;
import model.RequestTableCellRenderer;
import model.RequestTableModel;

public class LibraryServerRMIFrame extends JFrame 
					implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	// Private component
	private JLabel lblServerStatus;
	private JTable tblRequestStatus;
	private JButton btnBorrow;
	private JButton btnReturn;
	private JButton btnCancel;
	private JLabel lblBookNameLabel;
	private JLabel lblBorrowerNameLabel;
	private JLabel lblBorrowerContactLabel;
	private JLabel lblDateBorrowLabel;
	private JLabel lblDateReturnLabel;
	private JLabel lblExpiredFeeLabel;
	private JLabel lblBookName;
	private JLabel lblBorrowerName;
	private JLabel lblBorrowerContact;
	private JLabel lblDateBorrow;
	private JLabel lblDateReturn;
	private JLabel lblExpiredFee;
	private JLabel lblBookImage;
	private JPanel topPanel2;


	// Private dimension
	private int width = 950;
	private int height = 920;

	private char requestType;
	private int bookId;
	private int borrowerId;
	private int selectedRow;

	private RequestManager requestManager;
	private RequestTableModel tableModel;
	private RequestTableCellRenderer tableCellRenderer;

	/**
	 * 
	 * This constructor that initialize and organize the Swing components
	 * in the frame and create the remote of server
	 * 
	 * @param requestManager
	 * @throws RemoteException
	 */
	public LibraryServerRMIFrame (RequestInterface requestManager) 
			throws RemoteException {

		// Default frame setting
		this.setTitle("Library Management System: Server Side");
		this.setSize(new Dimension(width, height));
		this.setResizable(false);

		// Close on X
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new ShutDownAdapter(this));

		// Center the frame 
		this.setLocationRelativeTo(null);

		this.selectedRow = -1;
		this.requestManager = (RequestManager) requestManager;
		this.requestManager.setServerFrame(this);
		this.tableCellRenderer = new RequestTableCellRenderer();

		this.loadComponent();

	}

	/**
	 * 
	 * This method creates and arrange Swing components 
	 * for server status
	 * 
	 * @param font
	 * @return
	 */
	private JPanel getServerStatusPanel(Font font) {

		// Components to display server's status
		JPanel panel = new JPanel(new GridLayout(1, 2));

		JLabel lblServer = new JLabel ("Server Connection: ");
		lblServer.setHorizontalAlignment(JLabel.RIGHT);

		lblServerStatus = new JLabel("Waiting for request");
		lblServerStatus.setHorizontalAlignment(JLabel.LEFT);

		// Style the components
		lblServer.setFont(font);
		lblServerStatus.setFont(font);
		lblServer.setOpaque(true);
		lblServerStatus.setOpaque(true);

		// Organize component into the panel
		panel.add(lblServer);
		panel.add(lblServerStatus);

		return panel;

	}

	/**
	 * 
	 * This method creates and arrange Swing components for 
	 * the request status from client
	 * 
	 * @param font
	 * @return
	 */
	private JPanel getRequestStatus(Font font) {

		// Component to display request status
		JPanel panelRequest = new JPanel(new BorderLayout());

		// Style the component
		Vector<Request> requests = requestManager.getRequests();
		tableModel = new RequestTableModel(requests);
		tblRequestStatus = new JTable(tableModel);
		tblRequestStatus.setRowHeight(25);
		tblRequestStatus.setBorder
			(BorderFactory.createLineBorder(Color.BLACK, 3));
		tblRequestStatus.setFont
			(new Font (Font.SANS_SERIF, Font.PLAIN, 24));
		tblRequestStatus.getTableHeader().
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		tblRequestStatus.getTableHeader().
			setFont(new Font (Font.SANS_SERIF, Font.BOLD, 24));
		tblRequestStatus.getTableHeader().setReorderingAllowed(false);
		tblRequestStatus.setDefaultRenderer(Object.class, tableCellRenderer);
		tblRequestStatus.addMouseListener(this);

		// Organize component into the panel
		panelRequest.add(new JScrollPane(tblRequestStatus));

		return panelRequest;

	}

	/**
	 * 
	 * This method create and arrange the button in the frame
	 * 
	 * @return a button panel
	 */
	private JPanel getButtonPanel() {

		// Component to display button
		JPanel panelButton = new JPanel();
		btnBorrow = new JButton("Borrow");
		btnReturn = new JButton("Return");
		btnCancel = new JButton("Cancel");

		// Style the component
		btnBorrow.setFont(new Font (Font.SANS_SERIF, Font.PLAIN, 12));
		btnBorrow.setEnabled(false);
		btnBorrow.setActionCommand("borrow this book");
		btnBorrow.addActionListener(this);

		btnReturn.setFont(new Font (Font.SANS_SERIF, Font.PLAIN, 12));
		btnReturn.setEnabled(false);
		btnReturn.setActionCommand("return this book");
		btnReturn.addActionListener(this);

		btnCancel.setFont(new Font (Font.SANS_SERIF, Font.PLAIN, 12));
		btnCancel.setEnabled(false);
		btnCancel.setActionCommand("cancel this request");
		btnCancel.addActionListener(this);

		// Organize the component into panel
		panelButton.add(btnBorrow);
		panelButton.add(btnReturn);
		panelButton.add(btnCancel);

		return panelButton;

	}

	/**
	 * 
	 * This method create and arrange the panel to display
	 * the cover image of the book selected
	 * 
	 * @return the book cover panel
	 */
	private JPanel getBookCoverPanel() {

		// Component to display book image
		JPanel panelBookImage = new JPanel();
		ImageIcon icon = new ImageIcon();
		lblBookImage = new JLabel(icon);

		// Style the component
		lblBookImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		lblBookImage.setPreferredSize(new Dimension(270, 380));

		// Organize component into the panel
		panelBookImage.add(lblBookImage);

		return panelBookImage;
	}

	/**
	 * 
	 * This method create and arrange the panel to display
	 * the information of book
	 * 
	 * @return the book information panel
	 */
	private JPanel getBookDetailPanel() {

		// Create new font
		Font font = new Font (Font.SANS_SERIF, Font.PLAIN, 16);
		Font boldFont = new Font (Font.SANS_SERIF, Font.BOLD, 16);

		// Component to display detail of the borrow and return
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelBookDetailLabel = new JPanel(new GridLayout(6,1));
		JPanel panelBookDetail = new JPanel(new GridLayout(6,1));

		// Create label
		lblBookNameLabel = new JLabel("Book Name: ");
		lblBorrowerNameLabel = new JLabel("Borrower Name: ");
		lblBorrowerContactLabel = new JLabel("Borrower Contact: ");
		lblDateBorrowLabel = new JLabel("Date Borrow: ");
		lblDateReturnLabel = new JLabel("Date Return: ");
		lblExpiredFeeLabel = new JLabel("Expired Fee: ");
		lblBookName = new JLabel("-");
		lblBorrowerName = new JLabel("-");
		lblBorrowerContact = new JLabel("-");
		lblDateBorrow = new JLabel("-");
		lblDateReturn = new JLabel("-");
		lblExpiredFee = new JLabel("-");

		// Set font
		lblBookNameLabel.setFont(boldFont);
		lblBorrowerNameLabel.setFont(boldFont);
		lblBorrowerContactLabel.setFont(boldFont);
		lblDateBorrowLabel.setFont(boldFont);
		lblDateReturnLabel.setFont(boldFont);
		lblExpiredFeeLabel.setFont(boldFont);
		lblBookName.setFont(font);
		lblBorrowerName.setFont(font);
		lblBorrowerContact.setFont(font);
		lblDateBorrow.setFont(font);
		lblDateReturn.setFont(font);
		lblExpiredFee.setFont(font);

		// Align the text position  
		lblBookNameLabel.setHorizontalAlignment(JLabel.RIGHT);
		lblBorrowerNameLabel.setHorizontalAlignment(JLabel.RIGHT);
		lblBorrowerContactLabel.setHorizontalAlignment(JLabel.RIGHT);
		lblDateBorrowLabel.setHorizontalAlignment(JLabel.RIGHT);
		lblDateReturnLabel.setHorizontalAlignment(JLabel.RIGHT);
		lblExpiredFeeLabel.setHorizontalAlignment(JLabel.RIGHT);

		// Organize component into panel
		panelBookDetailLabel.add(lblBookNameLabel);
		panelBookDetailLabel.add(lblBorrowerNameLabel);
		panelBookDetailLabel.add(lblBorrowerContactLabel);
		panelBookDetailLabel.add(lblDateBorrowLabel);
		panelBookDetailLabel.add(lblDateReturnLabel);
		panelBookDetailLabel.add(lblExpiredFeeLabel);

		panelBookDetail.add(lblBookName);
		panelBookDetail.add(lblBorrowerName);
		panelBookDetail.add(lblBorrowerContact);
		panelBookDetail.add(lblDateBorrow);
		panelBookDetail.add(lblDateReturn);
		panelBookDetail.add(lblExpiredFee);

		panel.add(panelBookDetailLabel, BorderLayout.WEST);
		panel.add(panelBookDetail, BorderLayout.CENTER);

		return panel;
	}

	/**
	 * This method arrange the GUI component on the frame
	 */
	public void loadComponent() {

		// Get the server status panel and add to frame
		// Create the font
		Font font = this.getFontStyle();

		// Create and align the panel position in the layout
		JPanel halfTopPanel = new JPanel();
		halfTopPanel.setLayout(new BorderLayout());

		JPanel topPanel1 = this.getServerStatusPanel(font);
		halfTopPanel.add(topPanel1, BorderLayout.NORTH);

		this.topPanel2 =  this.getRequestStatus(font);
		halfTopPanel.add(topPanel2, BorderLayout.CENTER);


		JPanel halfBottomPanel = new JPanel();
		halfBottomPanel.setLayout(new BorderLayout());

		JPanel buttonPanel = this.getButtonPanel();
		halfBottomPanel.add(buttonPanel, BorderLayout.SOUTH);

		JPanel bookImagePanel = this.getBookCoverPanel();
		halfBottomPanel.add(bookImagePanel, BorderLayout.WEST);

		JPanel bookDetailPanel = this.getBookDetailPanel();
		halfBottomPanel.add(bookDetailPanel);

		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(halfTopPanel);
		panel.add(halfBottomPanel);

		this.add(panel); 

	}

	/**
	 * 
	 * This method is update server status when get request from client 
	 * 
	 */
	public void updateServerStatus() {

		String status = "Request receiving";

		try {

			for (int count = 0; count <= 3; count++) {
				// Set the status
				this.lblServerStatus.setText(status);
				status += ".";
				Thread.sleep(500);

			}

		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Set text
		this.lblServerStatus.setText("Waiting for request");

	}

	/**
	 * This method update the borrow status of the request sent to the client
	 * 
	 * @param status: request status
	 */
	public void updateRequestStatus (Vector<Request> requests) {

		// Arrange the data in the table
		tableModel = new RequestTableModel(requests);
		tblRequestStatus.setModel(tableModel);
		tblRequestStatus.changeSelection(selectedRow, 0, false, false);

		// Refresh the display
		this.topPanel2.removeAll();
		this.topPanel2.add(new JScrollPane(tblRequestStatus));
		this.topPanel2.revalidate();
		this.topPanel2.repaint();

	}


	/**
	 * This method define a font to a generic style.
	 * 
	 * @return font object
	 */
	private Font getFontStyle() {

		Font font = new Font (Font.SANS_SERIF, Font.PLAIN, 30);

		return font;

	}

	/**
	 * 
	 * This method checks whether the list of requests is empty
	 * 
	 * @return
	 */
	public boolean isRequestEmpty() {
		return requestManager.getRequests().isEmpty();
	}


	/**
	 * 
	 * This method display the information of return book 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void displayReturnBookInfo() 
			throws ClassNotFoundException, SQLException {

		// Get the value from table
		this.bookId = Integer.parseInt(tblRequestStatus.getValueAt
				(selectedRow, RequestTableModel.BOOKID).toString().split(",")[0]);
		
		// List to store the information
		Vector<String> info = requestManager.getReturnInfo(bookId, borrowerId);

		// String to store the information 
		String bookName = info.get(0);
		String borrowerName = info.get(1);
		String borrowerContact = info.get(2).substring(1);
		String dateBorrow = info.get(3);
		String dateReturn = Date.valueOf(LocalDate.now()).toString();
		String expiredFee = info.get(4);

		// Insert data into model
		lblBookName.setText(bookName);
		lblBorrowerName.setText(borrowerName);
		lblBorrowerContact.setText(borrowerContact);
		lblDateBorrow.setText(dateBorrow);
		lblDateReturn.setText(dateReturn);
		lblExpiredFee.setText(expiredFee);
		lblBookImage.setIcon(new ImageIcon("image/"+bookName+".jpg"));

		// Manage the buttons
		btnBorrow.setEnabled(false);
		btnReturn.setEnabled(true);
	}

	/**
	 * 
	 * This method manages the action from user 
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		String actionCommand = event.getActionCommand();
		
		// String to store message
		String message = new String();
		int length = 1;

		// Confirmation from user
		if (JOptionPane.showConfirmDialog(this, "Are you sure to " + actionCommand + "?",
				"Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
			return;

		try {

			switch(actionCommand) {

			// Action for borrow book
			case "borrow this book":

				// Borrow the book (update database)
				requestManager.borrowBook(bookId, borrowerId);
				// Display message
				message = "Borrow successfully";
				break;

			// Action for borrow book
			case "return this book":

				// Return the book (update database)
				requestManager.returnBook(bookId, borrowerId);
				// Display message
				message = "Return successfully";

				// Get value from table
				String[] bookIds = tblRequestStatus.getValueAt(selectedRow, 
						RequestTableModel.BOOKID).toString().split(",");
				length = bookIds.length;
				
				// Manage the unselected selection
				String remainingBookIds = new String();
				for (int index = 1; index < length; index++) 
					// Wrap date into string
					remainingBookIds += bookIds[index] + ",";

				// Rearrange the unselected books
				if (!remainingBookIds.isEmpty()) {
					remainingBookIds = remainingBookIds.substring
							(0, remainingBookIds.length()-1);
					tblRequestStatus.setValueAt
							(remainingBookIds, selectedRow, RequestTableModel.BOOKID);
					requestManager.getRequests().
							get(selectedRow).setTemperaryId(remainingBookIds);
					this.displayReturnBookInfo();
				}

				break;

			// Action for cancel request
			case "cancel this request":
				
				// Cancel request from client and undo the task done in client side
				if (requestType == 'B')
					requestManager.updateBookAvailability(bookId, true);
				message = "Request is cancelled";
				break;

			}

			// Manage the display
			if (length == 1) {

				// Clear the done request
				requestManager.removeRequest(tblRequestStatus.getSelectedRow());

				// Refresh the display
				this.getContentPane().removeAll();
				this.getContentPane().revalidate();
				this.getContentPane().repaint();
				this.loadComponent();

			} 
			else {

				this.updateRequestStatus(requestManager.getRequests());

			}


			JOptionPane.showMessageDialog(this, message);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * 
	 * This event manages the user's control of the table
	 * 
	 */
	@Override
	public void mousePressed(MouseEvent event) {

		// Get selected row
		selectedRow = tblRequestStatus.getSelectedRow();

		// Enable the button
		this.btnCancel.setEnabled(true);

		// Get the value from table
		this.requestType = tblRequestStatus.getValueAt(selectedRow, 
				RequestTableModel.REQUESTNO).toString().charAt(0);
		this.borrowerId = Integer.parseInt(tblRequestStatus.
				getValueAt(selectedRow, RequestTableModel.BORROWERID).toString());

		try {
			// Borrow request type
			if (requestType == 'B') {

				// Get the value from table
				this.bookId = Integer.parseInt(tblRequestStatus.
						getValueAt(selectedRow, RequestTableModel.BOOKID).toString());

				// List of information
				Vector<String> info = requestManager.getBorrowInfo(bookId, borrowerId);

				// Wrap the data
				String bookName = info.get(0);
				String borrowerName = info.get(1);
				String borrowerContact = info.get(2).substring(1);
				String dateBorrow = Date.valueOf(LocalDate.now()).toString();

				// Set the data to display
				lblBookName.setText(bookName);
				lblBorrowerName.setText(borrowerName);
				lblBorrowerContact.setText(borrowerContact);
				lblDateBorrow.setText(dateBorrow);
				lblBookImage.setIcon(new ImageIcon("image/"+bookName+".jpg"));

				// Manage the buttons
				btnBorrow.setEnabled(true);
				btnReturn.setEnabled(false);

			} 
			// Return request type
			else if (requestType == 'R') {

				// List of return books
				Vector<Book> returnBooks = requestManager.getReturnBooks(borrowerId);
				// Get the value from table
				String returnBookId = tblRequestStatus.
						getValueAt(selectedRow, RequestTableModel.BOOKID).toString();

				if (returnBookId.equals("Click to select books")) {

					// Clear the context
					returnBookId = "";
					// Get the  amount of return book
					int numberOfBooks = returnBooks.size();
					// Object to store information to display
					Object[] objects = new Object[numberOfBooks+1];
					objects[0] = "Please select the books that want to return: ";
					// Check box for selection of book
					Vector<JCheckBox> checkBoxs = new Vector<JCheckBox>();
					// Insert data into check box
					for (int index = 0; index < numberOfBooks; index++) {
						checkBoxs.add(new JCheckBox(returnBooks.get(index).getBookName()));
						objects[index+1] = checkBoxs.get(index);
					}
					// Display message
					if (JOptionPane.showConfirmDialog(this, objects, "Select books to return", 
							JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
						return;

					boolean checked = false;
					// Get the selection
					for (JCheckBox checkBox : checkBoxs) 
						if(checkBox.isSelected()) 
							for (Book book : returnBooks) 
								if (checkBox.getText().equals(book.getBookName())) {
									returnBookId += book.getBookId() + ",";
									checked = true;
								}

					if (!checked) {
						JOptionPane.showMessageDialog(this, "No book is selected");
						return;
					}
					// Rearrange the unselected books
					returnBookId = returnBookId.substring(0, returnBookId.length() - 1);
					tblRequestStatus.setValueAt(returnBookId, selectedRow, RequestTableModel.BOOKID);
					requestManager.getRequests().get(selectedRow).setTemperaryId(returnBookId);
				}
				// Display information of book
				this.displayReturnBookInfo();

			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
