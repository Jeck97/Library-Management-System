package controller.manager.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	

	public Connection getConnection () throws ClassNotFoundException, SQLException {
		
		// Search the database driver
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		// Establish connection
		Connection connection = DriverManager.getConnection
				("jdbc:mysql://localhost:3306/librarydb?serverTimezone=UTC","root","");  
		
		return connection;
		
	}

}
