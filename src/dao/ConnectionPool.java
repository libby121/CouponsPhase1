package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Singleton class. connection pool is needed in order to connect to data base, it
 * defines the amount of maximum connections in the program and includes the
 * methods that will be accessed by every DBDAO. By calling connection pool
 * instance, the single object of that class, DBDAO gets a "ready-to-use"
 * connection from the arrayList of connections (as long as the connection list
 * is not empty). After every single usage of a connection, it is restored to
 * the list. On main, and at the end of the program, the resource of connection
 * pool is closed.
 * 
 * @author μιαι
 * 
 */
public class ConnectionPool {

	private static ConnectionPool instance = new ConnectionPool();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	/**
	 * final unchangeable variable with no set/get
	 */
	private static final int MAX_CONNECTIONS = 10;

	// constants are usually static. here it has no meaning, only one variable
	/**
	 * singleton class creates only one instance, therefore the CTOR is private
	 * and unreachable.
	 */
	private ConnectionPool() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			for (int i = 1; i <= MAX_CONNECTIONS; i++) {
				connections
						.add(DriverManager
								.getConnection(
										"jdbc:mysql://localhost:3306/coupons_management_sys?serverTimezone=UTC",
										"root", "1234"));

			}

		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find class : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("cannot connect to DB " + e.getMessage());
		}

	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	/**
	 * two synchronized methods that would have been used together in the same
	 * program if the program was multi-threaded and one thread had to "wait"
	 * for a connection(maximum connections at a time-10). restore connection
	 * method by itself is used after every data base communication,a connection
	 * is restored to the arrayList for further usage.
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		while (connections.size() == 0)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		Connection con = connections.get(0);
		connections.remove(0);
		return con;
	}

	public synchronized void restoreConnection(Connection connection) {
		connections.add(connection);

		/**
		 * notify()-"gentle awakening" of the sleeping thread from
		 * connectionPool, if needed
		 */
		notify();

	}

	public void closeAllConnections() {
		ArrayList<Connection> temp=new ArrayList<Connection> (connections);
		
		for (Connection connection : temp) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}