package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {

	public static Connection connect(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e1) {
			System.err.println("Could not find driver");
			e1.printStackTrace();
			System.exit(0);

		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);

		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);

		}
        System.out.println("Driver Found...");
        try {
			conn = DriverManager.getConnection("jdbc:mysql://5.196.123.198:3306/" + "P2I", "G222_B", "G222_B");
		} catch (SQLException e1) {
			System.err.println("Could not connect to the database");
			e1.printStackTrace();
			System.exit(0);
		}
        System.out.println("Connected...");
        return conn;
	}
	
		
    
}
