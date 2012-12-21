package nl.ecb.samp.ericrp.persistance.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.login.AccountNotFoundException;

import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
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
	public void saveAccount(Account account){
		/*//TODO implement
		long startTime = System.currentTimeMillis();
		Account acc = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(" INSERT INTO accounts (Username, Password) VALUES ('"+account+"', value2, value3,...)" );

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
		return acc;*/
	}
	public void createAccount(Connection connection, String username,String password,String email) throws AccountAlreadyCreatedException{
		//TODO implement
		long startTime = System.currentTimeMillis();
		try {
			Statement statement = connection.createStatement();
			statement.execute(" INSERT INTO accounts (Username, Password,Email) VALUES ('"+username+"', '"+password+"', '"+email+ " ')" );
			System.out.println(connection.getAutoCommit());

		}
		catch (SQLException ex){
			if(ex.getErrorCode() == 1062){
				throw new AccountAlreadyCreatedException();
			}else{
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("time: "+duration);
	}
}
