package nl.ecb.samp.ericrp.persistance;

import java.sql.Connection;

import javax.security.auth.login.AccountNotFoundException;

import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.model.Account;
import nl.ecb.samp.ericrp.persistance.mysql.MysqlAccountCRUD;
import nl.ecb.samp.ericrp.persistance.mysql.MysqlConnector;

public class MysqlAdapter {
	private static MysqlAdapter INSTANCE;
	private MysqlConnector connector;
	private MysqlAccountCRUD crudAccount;
	
	public MysqlAdapter(){
		connector = new MysqlConnector();
		crudAccount = new MysqlAccountCRUD();
	}
	public static MysqlAdapter getInstance(){
		if(INSTANCE == null){
			INSTANCE = new MysqlAdapter();
		}
		return INSTANCE;
	}
	public Account getAccount(String username, String password) throws AccountNotFoundException{
		return crudAccount.getAccount(connector.getConnection(), username, password);
		
	}
	public void saveAccount(Account account) {
		crudAccount.saveAccount(account);
	}
	public void createAccount(String username,String password,String email) throws AccountAlreadyCreatedException{
		crudAccount.createAccount(connector.getConnection(), username,password,email);
	}
}
