package nl.ecb.samp.ericrp.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.exceptions.CharacterAlreadyCreatedException;
import nl.ecb.samp.ericrp.model.Account;
import nl.ecb.samp.ericrp.model.Character;
import nl.ecb.samp.ericrp.model.Character.Gender;
import nl.ecb.samp.ericrp.persistance.mysql.MysqlAccountCRUD;
import nl.ecb.samp.ericrp.persistance.mysql.MysqlCharacterCRUD;
import nl.ecb.samp.ericrp.persistance.mysql.MysqlConnector;

public class MysqlAdapter {
	private static MysqlAdapter INSTANCE;
	private MysqlConnector connector;
	private MysqlAccountCRUD crudAccount;
	private MysqlCharacterCRUD crudCharacter;

	public MysqlAdapter() {
		connector = new MysqlConnector();
		crudAccount = new MysqlAccountCRUD();
		crudCharacter = new MysqlCharacterCRUD();
	}

	public static MysqlAdapter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MysqlAdapter();
		}
		return INSTANCE;
	}

	public Account getAccount(String username, String password)
			throws AccountNotFoundException {
		return crudAccount.getAccount(connector.getConnection(), username,
				password);

	}

	public void saveAccount(Account account) {
		crudAccount.saveAccount(connector.getConnection(), account);
	}

	public void createAccount(String username, String password, String email)
			throws AccountAlreadyCreatedException {
		crudAccount.createAccount(connector.getConnection(), username,
				password, email);
	}

	public boolean isAccount(Player p) {
		return crudAccount.isAccount(connector.getConnection(), p);
	}

	public int createCharacter(Account a, Character c) throws CharacterAlreadyCreatedException {
		return crudCharacter.createCharacter(connector.getConnection(), a, c);
	}

	public void saveCharacter(Character c) {
		crudCharacter.saveCharacter(connector.getConnection(), c);
	}

	public List<Character> getCharacters(int userID) {
		return crudCharacter.getCharacters(connector.getConnection(), userID);

	}

	public void deleteCharacter(Character c) {
		crudCharacter.deleteCharacter(connector.getConnection(), c);
	}

	public boolean isCharacterNameAvalible(String name) {
		return crudCharacter.isCharacterNameAvalible(connector.getConnection(),
				name);
	}
}
