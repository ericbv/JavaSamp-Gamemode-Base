package nl.ecb.samp.ericrp.persistance.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.account.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.account.model.Account;
import nl.ecb.samp.ericrp.main.Main;

public class MysqlAccountCRUD {
	public Account getAccount(Connection connection, String username, String password) throws AccountNotFoundException{
		Account acc = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE Username='"+username+"'AND Password='"+password+"'");
			if(resultSet.next()){
				String email = resultSet.getString(4);
				int iD = resultSet.getInt(1);
				List<Character> characters = new ArrayList<Character>();
				acc = Account.load(username, password, email, iD, characters);

			}
			else{
				throw new AccountNotFoundException();
			}

		}
		catch (SQLException ex){
			proccesMysqlError(ex);
		}
		return acc;

	}
	public void saveAccount(Connection connection, Account account){
		try {
			Statement statement = connection.createStatement();
			statement.execute(" UPDATE accounts SET Username='"+account.getUsername()+"', Password='"+account.getPassword()+"', Email='"+account.getEmail()+"' WHERE AcountID="+account.getID()+"");
		}
		catch (SQLException ex){
			proccesMysqlError(ex);
		}
	}
	public int createAccount(Connection connection, String username,String password,String email) throws AccountAlreadyCreatedException{
		try {
			Statement statement = connection.createStatement();
			statement.execute(" INSERT INTO accounts (Username, Password,Email) VALUES ('"+username+"', '"+password+"', '"+email+ " ')" );
			System.out.println(connection.getAutoCommit());
			ResultSet resultSet = statement.executeQuery("SELECT AcountID FROM accounts WHERE Username='"+username+"'AND Password='"+password+"'");
			if(resultSet.next()){
				int iD = resultSet.getInt(1);
				return iD;
			}

		}
		catch (SQLException ex){
			if(ex.getErrorCode() == 1062){
				throw new AccountAlreadyCreatedException();
			}else{
				proccesMysqlError(ex);
			}
		}
		return -1;
	}
	public boolean isAccount(Connection connection, Player p) {
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
		Main.logger().error("ERROR 001 accountcrud");
		return false;
	}
	private void proccesMysqlError(SQLException ex){
		Main.logger().error("SQLException: " + ex.getMessage());
		Main.logger().error("SQLState: " + ex.getSQLState());
		Main.logger().error("VendorError: " + ex.getErrorCode());
	}
}
