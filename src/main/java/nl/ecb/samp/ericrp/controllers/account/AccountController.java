package nl.ecb.samp.ericrp.controllers.account;

import javax.security.auth.login.AccountNotFoundException;


import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
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
	 * @throws playeridAlreadyLoggedInException when the player is already logged in
	 */
	public void login(Player p,String username, String password) throws AccountNotFoundException, playeridAlreadyLoggedInException {
		store.setAccount(p, MysqlAdapter.getInstance().getAccount(username,password));
	}
	public void logout(Player p) throws NotLoggedInException {
		MysqlAdapter.getInstance().saveAccount(store.getAccount(p));
		store.removeAccount(p);
	}
	public void register(Player p,String Email,String username, String password) throws AccountAlreadyCreatedException {
		MysqlAdapter.getInstance().createAccount(username, password, Email)	;
		}
	public boolean isRegisterdMember(Player p){
		return MysqlAdapter.getInstance().isAccount(p);
	}
}
