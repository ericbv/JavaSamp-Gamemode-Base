package nl.ecb.samp.ericrp.persistance.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.main.Main;
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
			proccesMysqlError(ex);
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("time: "+duration);
		return acc;

	}
	public void saveAccount(Connection connection, Account account){
		long startTime = System.currentTimeMillis();
		try {
			Statement statement = connection.createStatement();
			statement.execute(" UPDATE accounts SET Username='"+account.getUsername()+"', Password='"+account.getPassword()+"', Email='"+account.getEmail()+"' WHERE AcountID="+account.getID()+"");
		}
		catch (SQLException ex){
			proccesMysqlError(ex);
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("time: "+duration);
	}
	public void createAccount(Connection connection, String username,String password,String email) throws AccountAlreadyCreatedException{
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
				proccesMysqlError(ex);
			}
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("time: "+duration);
	}
	public boolean isAccount(Connection connection, Player p) {
		long startTime = System.currentTimeMillis();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE Username='"+p.getName()+"'");
			if(!resultSet.next()){
				return false;
			}
			else{
				return true;
			}

		}
		catch (SQLException ex){
			proccesMysqlError(ex);
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("time: "+duration);
		Main.logger().error("ERROR 001 accountcrud");
		return false;
	}
	private void proccesMysqlError(SQLException ex){
		Main.logger().error("SQLException: " + ex.getMessage());
		Main.logger().error("SQLState: " + ex.getSQLState());
		Main.logger().error("VendorError: " + ex.getErrorCode());
	}
}
