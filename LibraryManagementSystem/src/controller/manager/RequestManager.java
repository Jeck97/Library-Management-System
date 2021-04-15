package controller.manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Vector;

import controller.RequestInterface;
import controller.manager.connector.Connector;
import model.Book;
import model.Borrower;
import model.Request;
import view.frame.LibraryServerRMIFrame;
/**
 * 
 * This class manages the function used in client and server sides
 * 
 * @author Chon Yao Jun
 *
 */
public class RequestManager extends UnicastRemoteObject 
								implements RequestInterface {

	private static final long serialVersionUID = 1L;

	// Object to represent the integer part of 
	// request number for borrow and return
	private static int borrowNo = 0;
	private static int returnNo = 0;

	// Vector to record all the requests
	private static Vector<Request> requests = new Vector<Request>();

	// Connector to connect database
	private Connector dbConnector;

	// The server frame
	private LibraryServerRMIFrame serverFrame;

	// This method connect database when the server starts
	public  RequestManager() throws RemoteException {

		super();

		// Open connection to connect to the database
		dbConnector = new Connector();

	}

	/**
	 *  This method retrieve all the book names from database
	 *
	 * @return List of book names
	 * 
	 * @throws RemoteException, SQLException, ClassNotFoundException
	 */
	@Override
	public Vector<String> getBookNames() 
			throws RemoteException, SQLException, ClassNotFoundException {

		// Receive connection with database
		Connection conn = null;

		// Statement for SQL statement 
		Statement stmt = null;

		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		// To store the list of all the book names
		Vector<String> bookNames = new Vector<String>();

		try {

			// SQL statement 
			// Retrieve the book name from the book table in database
			String sql = "SELECT bookName FROM book";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Create and send statement to the database
			stmt = conn.createStatement();

			// Execute the SQL statement and return the table of data
			rs = stmt.executeQuery(sql);

			// Wrap data into object
			while(rs.next())

				// Add the book name from the table of data into the
				// List of book names one by one
				bookNames.add(rs.getString(1));

			// Return the list of book names
			return bookNames;

		} finally {

			// Close all closable objects
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * 
	 * This method retrieve a book info
	 * 
	 * @param the name of the book
	 * @return a book including all the information
	 * 
	 */
	@Override
	public Book getBookInfo(String name) 
			throws RemoteException, SQLException, ClassNotFoundException {

		// Receive connection with database
		Connection conn = null;

		// Statement for SQL statement 
		Statement stmt = null;

		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		try {
			// SQL statement 
			// Retrieve the book name from the book table in database
			String sql = "SELECT * FROM book WHERE bookName = '" + name + "'";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Create and send statement to the database
			stmt = conn.createStatement();

			// Execute the SQL statement and return the table of data
			rs = stmt.executeQuery(sql);

			// Wrap data into object
			while(rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt(1));
				book.setBookName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setDatePublished(rs.getDate(4));
				book.setGenre(rs.getString(5));
				book.setLoanRate(rs.getDouble(6));
				book.setAvailability(rs.getBoolean(7));
				book.setSynopsis(rs.getString(8));

				// Return the book 
				return book;
			}

		} finally {
			// Close all closable objects
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}	

		return null;
	}

	/**
	 * 
	 * This method manages the duplication of borrower
	 * 
	 * @param the borrower object
	 * @return the flag of the duplication of borrower
	 */
	@Override
	public boolean isDuplicateReturn(Borrower borrower)
			throws RemoteException, SQLException, ClassNotFoundException {

		// Boolean to check the duplication of borrower 
		boolean duplicate = false;

		// Receive connection with database
		Connection conn = null;

		// Statement for SQL statement 
		Statement stmt = null;

		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		try {

			// SQL statement 
			// Retrieve the borrower ID by using the contact number
			String sql = "SELECT BORROWERID FROM BORROWER WHERE CONTACT"
					+ " = '" + borrower.getContact() + "'";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Create and send statement to the database
			stmt = conn.createStatement();

			// Execute the SQL statement and return the table of data
			rs = stmt.executeQuery(sql);

			// If there has record
			if(rs.next()) {

				// Integer to store the borrower id retrieve from the database
				int borrowerId = rs.getInt(1);

				// Loop all the requests
				for (Request request : requests) 

					// Compare record with the borrower id
					if (borrowerId == request.getBorrower().getId() 
						&& request.getType() == 'R') {

						// Assign the duplication to true
						duplicate = true;
						break;
					}
			} else
				// Assign the duplication to false
				duplicate = false;

		} finally {
			// Close all closable objects
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return duplicate;
	}

	/**
	 * 
	 * This method checks whether the person is a borrower
	 * 
	 * @param the person's contact number
	 * @return the exist of borrower
	 * 
	 */

	@Override
	public boolean isAnyBookReturn(String contact) 
			throws RemoteException, SQLException, ClassNotFoundException {

		// Receive connection with database
		Connection conn = null;

		// Statement for SQL statement 
		Statement stmt = null;

		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		boolean found = false;

		try {
			// SQL statement
			// Count the book borrowed
			String sql = "SELECT COUNT(*) FROM borrow_return NATURAL JOIN "
					+ "borrower WHERE contact = '" + contact + "' AND "
					+ "dateReturn IS NULL";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Create and send statement to the database
			stmt = conn.createStatement();

			// Execute the SQL statement and return the table of data
			rs = stmt.executeQuery(sql);

			// If there has records
			while(rs.next()) 
				if(rs.getInt(1) > 0) 
					found = true;

		} finally {
			// Close all closable objects
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return found;

	}

	/**
	 * 
	 * This method manages the requests from client side
	 * 
	 * @param type
	 * @param borrower
	 * @param book
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	@Override
	public int requestHandler(char type, Borrower borrower, Book book) 
			throws RemoteException, SQLException, ClassNotFoundException {

		// Set the book unable to borrow
		book.setAvailability(false);

		// Integer to store the request number
		int number = requestNoGenerator(type);

		// Create new request
		Request request = new Request(type, number, borrower, book);

		// Set the book unable to borrow in database
		this.updateBookAvailability(book.getBookId(), false);

		// Add request into list of requests
		requests.add(request);

		// Update the status
		new Thread() {
			public void run() {
				serverFrame.updateServerStatus();
				serverFrame.updateRequestStatus(requests);
			}
		}.start();

		return number;

	}

	/**
	 * 
	 * This method retrieve the borrower from database
	 * 
	 * @param contact
	 * return borrower
	 */
	@Override
	public Borrower getBorrowerByContact(String contact)
			throws RemoteException, SQLException, ClassNotFoundException {

		// Receive connection with database
		Connection conn = null;

		// Statement for SQL statement 
		Statement stmt = null;

		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		Borrower borrower = new Borrower();

		try {
			// SQL statement
			// Get ID and name of borrower by using contact number
			String sql = "SELECT borrowerID, name FROM borrower WHERE contact"
					+ " = '" + contact + "'";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Create and send statement to the database
			stmt = conn.createStatement();

			// Execute the SQL statement and return the table of data
			rs = stmt.executeQuery(sql);

			// Wrap the data
			if(rs.next()) {
				borrower.setId(rs.getInt(1));
				borrower.setName(rs.getString(2));
			}

		} finally {
			// Close all closable objects
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return borrower;
	}

	/**
	 * 
	 * This method add borrower record to database
	 * 
	 * @param borrower
	 * @return borrowerId
	 * 
	 */
	@Override
	public int addBorrower(Borrower borrower) 
			throws RemoteException, ClassNotFoundException, SQLException {

		// Receive connection with database
		Connection conn = null;
		// Prepare SQL statement to insert values
		PreparedStatement preStmt = null;
		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		try {
			// SQL statement
			// Add new borrower into database
			String sql = "INSERT INTO BORROWER (NAME, CONTACT) VALUES (?, ?)";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Insert data into SQL statment
			preStmt = conn.prepareStatement(sql, 
					PreparedStatement.RETURN_GENERATED_KEYS);
			preStmt.setString(1, borrower.getName());
			preStmt.setString(2, borrower.getContact());
			preStmt.executeUpdate();

			// Get auto generated borrower Id from database
			rs = preStmt.getGeneratedKeys();

			// Return borrower Id
			while(rs.next())
				return rs.getInt(1);

		} finally {
			// Close all closable objects
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return 0;
	}


	/**
	 * 
	 * This method update borrower name with same contact number
	 * 
	 * @param borrower
	 * 
	 */
	@Override
	public void updateBorrowerName(Borrower borrower)
			throws RemoteException, ClassNotFoundException, SQLException {

		// Receive connection with database
		Connection conn = null;
		// Prepare SQL statement to insert values
		PreparedStatement preStmt = null;


		try {
			//SQL statement 
			//Update borrower name by using contact number
			String sql = "UPDATE borrower SET name = ? WHERE contact = ?";

			// Get connection from the database
			conn = dbConnector.getConnection();
			
			// Insert data into SQL statement
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, borrower.getName());
			preStmt.setString(2, borrower.getContact());
			preStmt.executeUpdate();

		} finally {
			// Close all closable objects
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

	}

	/**
	 * 
	 * This method get the info of borrower and book
	 * Borrow book
	 * 
	 * @param bookId
	 * @param borrowerId
	 * @return list of info of borrower and book
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Vector<String> getBorrowInfo(int bookId, int borrowerId) 
			throws ClassNotFoundException, SQLException {
		
		// Receive connection with database
		Connection conn = null;
		
		// Statement for SQL statement 
		Statement stmtBook = null;
		Statement stmtBorrower = null;
		
		// To store the table of data from the result of executed SQL statement
		ResultSet rsBook = null;
		ResultSet rsBorrower = null;

		// List to store the info of book and borrower
		Vector<String> info = new Vector<String>();

		try {

			// Retrieve the book name by using book id
			String sqlBook = "SELECT bookName FROM book WHERE bookID = '" 
					+ bookId + "'";

			// Retrieve the name and contact number of borrower by using borrower id 
			String sqlBorrower = "SELECT name, contact FROM borrower "
					+ "WHERE borrowerID = '" + borrowerId + "'";

			// Get connection from the database
			conn = dbConnector.getConnection();
			
			// Create and send statement to the database
			stmtBook = conn.createStatement();
			stmtBorrower = conn.createStatement();
			
			// Execute the SQL statement and return the table of data
			rsBook = stmtBook.executeQuery(sqlBook);
			rsBorrower = stmtBorrower.executeQuery(sqlBorrower);

			// Wrap the data into list
			while (rsBook.next()) {
				info.add(rsBook.getString(1));
			}
			while (rsBorrower.next()) {
				info.add(rsBorrower.getString(1));
				info.add(rsBorrower.getString(2));
			}

		} finally {
			// Close all closable objects
			if (rsBook != null)
				rsBook.close();
			if (rsBorrower != null)
				rsBorrower.close();
			if (stmtBook != null)
				stmtBook.close();
			if (stmtBorrower != null)
				stmtBorrower.close();
			if (conn != null)
				conn.close();
		}

		return info;

	}

	/**
	 * 
	 * This method get the info of borrower and book
	 * Return book
	 * 
	 * @param bookId
	 * @param borrowerId
	 * @return list of info of borrower and book
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Vector<String> getReturnInfo(int bookId, int borrowerId) 
			throws ClassNotFoundException, SQLException {

		// Receive connection with database
		Connection conn = null;

		// Statement for SQL statement 
		Statement stmt = null;

		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		// List to store the info of book and borrower
		Vector<String> info = new Vector<String>();
		
		// Date to record date borrow
		Date dateBorrow = null;
		
		// The rate needed to pay
		double loanRate = 0;
		
		// The fee of return book late
		double expiredFee = 0;

		try {
			// SQL statement
			// Get the data from database
			String sql = "SELECT bookName, name, contact, dateBorrow, loanRate "
					+ "FROM book NATURAL JOIN borrow_return NATURAL JOIN "
					+ "borrower WHERE bookID = '" + bookId + "' AND borrowerID "
					+ "= '" + borrowerId + "' AND dateReturn IS NULL";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Create and send statement to the database
			stmt = conn.createStatement();

			// Execute the SQL statement and return the table of data
			rs = stmt.executeQuery(sql);

			// Wrap data into object
			while (rs.next()) {

				info.add(rs.getString(1));
				info.add(rs.getString(2));
				info.add(rs.getString(3));
				info.add(rs.getString(4));

				dateBorrow = rs.getDate(4);
				loanRate = rs.getDouble(5);

			}
			
			// Caluclate the fee if return book late
			expiredFee = this.calculateExpiredFee(loanRate, dateBorrow);
			info.add("RM " + String.format("%.2f", expiredFee));

		} finally {
			// Close all closable objects
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return info;

	}

	/**
	 * 
	 * This method calculates the fee if return book late
	 * 
	 * @param loanRate
	 * @param dateBorrow
	 * @return fee
	 */
	private double calculateExpiredFee(double loanRate, Date dateBorrow) {

		// Date of borrow and return
		LocalDate dateReturnExpected = dateBorrow.toLocalDate().plusDays(14);
		LocalDate dateReturnActual = LocalDate.now();

		// Calculate the period of borrow
		long daysBetween = ChronoUnit.DAYS.
				between(dateReturnExpected, dateReturnActual);

		if (daysBetween <= 0)
			return 0;

		// Return amount of fee
		return loanRate * daysBetween;

	}

	/**
	 * 
	 * This method get the info book borrowed
	 * Return book
	 * 
	 * @param borrowerId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Vector<Book> getReturnBooks(int borrowerId) 
			throws SQLException, ClassNotFoundException {

		// Receive connection with database
		Connection conn = null;

		// Statement for SQL statement 
		Statement stmt = null;

		// To store the table of data from the result of executed SQL statement
		ResultSet rs = null;

		Vector<Book> books = new Vector<Book>();

		try {

			// SQL statement
			//  Get the book id borrowed and book name by using the borrower id
			String sql = "SELECT bookID, bookName FROM book NATURAL JOIN "
					+ "borrow_return WHERE borrowerID = '" + borrowerId 
					+ "' AND dateReturn IS NULL";

			// Get connection from the database
			conn = dbConnector.getConnection();

			// Create and send statement to the database
			stmt = conn.createStatement();

			// Execute the SQL statement and return the table of data
			rs = stmt.executeQuery(sql);

			// Wrap the data into object
			while (rs.next()) {

				Book book = new Book();
				book.setBookId(rs.getInt(1));
				book.setBookName(rs.getString(2));

				books.add(book);

			}

		} finally {
			// Close all closable objects
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return books;
	}

	/**
	 * 
	 * This method record the borrower and book borrow
	 * Borrow book
	 * 
	 * @param bookId
	 * @param borrowerId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void borrowBook(int bookId, int borrowerId) 
			throws SQLException, ClassNotFoundException {

		// Define closable objects
		Connection conn = null;
		PreparedStatement preStmt = null;

		try {

			// SQL statement
			// Add record into database
			String sql = "INSERT INTO BORROW_RETURN "
					+ "(BORROWERID, BOOKID, DATEBORROW) VALUES (?,?,?)";

			// Establish connection
			conn = dbConnector.getConnection();

			// Set parameter
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, borrowerId);
			preStmt.setInt(2, bookId);
			preStmt.setDate(3, Date.valueOf(LocalDate.now()));

			// Execute SQL
			preStmt.executeUpdate();

		} finally {
			// Close all closable objects
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

	}

	/**
	 * 
	 * This method manage the record when the borrower returns book
	 * 
	 * @param bookId
	 * @param borrowerId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void returnBook(int bookId, int borrowerId) 
			throws ClassNotFoundException, SQLException {

		// Define closable objects
		Connection conn = null;
		PreparedStatement preStmt = null;

		try {
			// SQL statement
			// Update record in database
			String sql = "UPDATE BORROW_RETURN SET DATERETURN = ? "
					+ "WHERE BORROWERID = ? AND BOOKID = ?";

			// Establish connection
			conn = dbConnector.getConnection();

			// Set parameter
			preStmt = conn.prepareStatement(sql);
			preStmt.setDate(1, Date.valueOf(LocalDate.now()));
			preStmt.setInt(2, borrowerId);
			preStmt.setInt(3, bookId);

			// Execute SQL
			preStmt.executeUpdate();

			this.updateBookAvailability(bookId, true);

		} finally {
			// Close all closable objects
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

	}


	/**
	 * 
	 * This method update the availability of book
	 * 
	 * @param bookId
	 * @param setAvailability
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateBookAvailability(int bookId, boolean setAvailability) 
			throws ClassNotFoundException, SQLException {

		// Define closable objects
		Connection conn = null;
		PreparedStatement preStmt = null;

		// Integer to define the availability of book
		int availability = setAvailability ? 1 : 0;

		try {
			// SQL statement
			// Update availability of book in database
			String sql = "UPDATE BOOK SET AVAILABILITY = ? WHERE BOOKID = ?";

			// Establish connection
			conn = dbConnector.getConnection();

			// Set parameter
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, availability);
			preStmt.setInt(2,  bookId);

			// Execute SQL
			preStmt.executeUpdate();

		} finally {
			// Close all closable objects
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

	}

	/**
	 * 
	 * This method is number generator for request
	 * 
	 * @param type
	 * @return request number
	 */
	private int requestNoGenerator(char type) {

		if(type == 'B') 
			return borrowNo += 1;

		else
			return returnNo += 1;

	}

	/**
	 * 
	 * This method get the requests
	 * 
	 * @return List of requests
	 */
	public Vector<Request> getRequests() {

		return requests;

	}

	/**
	 * 
	 * This method removes the request from the list
	 * 
	 * @param index
	 */
	public void removeRequest(int index) {

		requests.remove(index);

	}

	/**
	 * 
	 * This method set the server frame into this class
	 * 
	 * @param serverFrame
	 */
	public void setServerFrame(LibraryServerRMIFrame serverFrame) {

		this.serverFrame = serverFrame;

	}


}