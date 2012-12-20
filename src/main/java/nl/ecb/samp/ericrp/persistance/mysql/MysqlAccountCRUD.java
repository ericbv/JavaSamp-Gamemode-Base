package nl.ecb.samp.ericrp.persistance.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.login.AccountNotFoundException;

import nl.ecb.samp.ericrp.model.Account;

public class MysqlAccountCRUD {
	public Account getAccount(Connection connection, String username, String password) throws AccountNotFoundException{
		long startTime = System.currentTimeMillis();
		Account acc = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE Username='"+username+"'AND Password='"+password+"'");
			if(resultSet.next()){
				String email = resultSet.getString(4);
				int iD = resultSet.getInt(1);
				acc = Account.load(username, password, email, iD);
			}
			else{
				throw new AccountNotFoundException();
			}
		
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("time: "+duration);
		return acc;

	}
}
