package nl.ecb.samp.ericrp.controllers.account;

import javax.security.auth.login.AccountNotFoundException;


import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playeridAlreadyLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;
import nl.ecb.samp.ericrp.persistance.MysqlAdapter;

public class AccountController {
	private AccountStore store;
	/**
	 * This is the constructor for the account controller.
	 */
	public AccountController()
	{
		this.store = AccountStore.getInstance();
	}
	/**
	 * 
	 * @param p the player thats making a attempt to login.
	 * @param username username of the account youre trying to load.
	 * @param password the password of the account youre trying to load
	 * @throws AccountNotFoundException when no acount is found using the credentials
	 * @throws playeridAlreadyLoggedInException when the platyer is already logged in
	 */
	protected void login(Player p,String username, String password) throws AccountNotFoundException, playeridAlreadyLoggedInException {
		store.setAccount(p, MysqlAdapter.getInstance().getAccount(username,password));
	}
	protected void logout(Player p) throws NotLoggedInException {
		MysqlAdapter.getInstance().saveAccount(store.getAccount(p));
		store.removeAccount(p);
	}
}
