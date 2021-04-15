package model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * This class design the table
 * 
 * @author Chon Yao Jun
 *
 */
public class RequestTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	// Integer to store the specified column
	public static final int NUMBER = 0;
	public static final int REQUESTNO = 1;
	public static final int BORROWERID = 2;
	public static final int BOOKID = 3;
	
	/**
	 * 
	 * This method insert the data into table
	 * 
	 * @param requests
	 */
	
	public RequestTableModel(Vector<Request> requests) {
		
		// Integer to store the number of rows
		int size = requests.size();
		
		// 2D array to store data
		String[][] data = new String[size][4];
		
		// Wrap data into array
		for (int index = 0; index < size; index++) {
			
			int bookId = requests.get(index).getBook().getBookId();
			
			data[index][0] = String.valueOf(index + 1);
			data[index][1] = requests.get(index).getRequestNo();
			data[index][2] = String.valueOf(requests.get(index).
					getBorrower().getId());
			data[index][3] = bookId == 0 ? requests.get(index).
					getTemperaryId() : String.valueOf(bookId);

		}
		
		// Set the header
		this.setDataVector(data, new String[] 
				{ "No.", "Request No.", "Borrower ID", "Book ID" });
	}
	
	/**
	 * 
	 * This method disable edit table
	 * 
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		
		return false;
		
	}

}
