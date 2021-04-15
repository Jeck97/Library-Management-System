package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

import model.Book;
import model.Borrower;

/**
 * 
 * This interface represents RequestManager
 * 
 * All the methods will be used and explained in RequestManager
 * 
 * @author Chon Yao Jun
 *
 */
public interface RequestInterface extends Remote {

	public Vector<String> getBookNames() 
			throws RemoteException, SQLException, ClassNotFoundException;

	public Book getBookInfo(String name) 
			throws RemoteException, SQLException, ClassNotFoundException;
	
	public boolean isDuplicateReturn(Borrower borrower)
			throws RemoteException, SQLException, ClassNotFoundException;
	
	public boolean isAnyBookReturn(String contact) 
			throws RemoteException, SQLException, ClassNotFoundException;

	public int requestHandler(char type, Borrower borrower, Book book) 
			throws RemoteException, SQLException, ClassNotFoundException;
	
	public Borrower getBorrowerByContact(String contact)
			throws RemoteException, SQLException, ClassNotFoundException;
	
	public int addBorrower(Borrower borrower) 
			throws RemoteException, ClassNotFoundException, SQLException;
	
	public void updateBorrowerName(Borrower borrower) 
			throws RemoteException, ClassNotFoundException, SQLException;

}
