package nl.ecb.samp.ericrp.persistance.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;

import nl.ecb.samp.ericrp.main.Main;

public class MysqlConnector {
	public MysqlConnector(){
		this.Init();
	}
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn =
					DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaSamp?user=root");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
	public void Init() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				Main.logger().error("nom",e);
			}
	}

}
