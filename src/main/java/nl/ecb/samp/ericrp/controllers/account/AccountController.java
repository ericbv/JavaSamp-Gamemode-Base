package nl.ecb.samp.ericrp.controllers.account;


import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import nl.ecb.samp.ericrp.events.AccountRequestLoginEvent;
import nl.ecb.samp.ericrp.events.AccountRequestLogoutEvent;
import nl.ecb.samp.ericrp.events.PlayerLoginEvent;
import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playeridAlreadyLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.persistance.MysqlAdapter;
import nl.ecb.samp.ericrp.model.Account;

public class AccountController {
	private AccountStore store;
	private Shoebill shoebill;
	private ManagedEventManager eventManager;

	/**
	 * This is the constructor for the account controller.
	 * 
	 * @param rootEventManager
	 * @param shoebill
	 */
	public AccountController(Shoebill shoebill, EventManager rootEventManager) {
		this.store = AccountStore.getInstance();
		this.shoebill = shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
	}

	/**
	 * 
	 * @param p
	 *            the player thats making a attempt to login.
	 * @param username
	 *            username of the account youre trying to load.
	 * @param password
	 *            the password of the account youre trying to load
	 * @throws AccountNotFoundException
	 *             when no acount is found using the credentials
	 * @throws playeridAlreadyLoggedInException
	 *             when the player is already logged in
	 */
	public void login(Player p, String username, String password)
			throws AccountNotFoundException, playeridAlreadyLoggedInException {
		Account a = MysqlAdapter.getInstance().getAccount(username, password); //Load account from things
		eventManager.dispatchEvent(new AccountRequestLoginEvent(a), a); //Signal all other controlers to fill account with their related data.
		store.setAccount(p, a); // Put user in logged in store
		eventManager.dispatchEvent(new PlayerLoginEvent(p), p);//Signal the other controllers the player is spawning.
	}

	public void logout(Player p) throws NotLoggedInException {
		Account a = store.getAccount(p);
		eventManager.dispatchEvent(new AccountRequestLogoutEvent(a), a);
		MysqlAdapter.getInstance().saveAccount(store.getAccount(p));
		store.removeAccount(p);
	}

	public void register(Player p, String Email, String username,
			String password) throws AccountAlreadyCreatedException {
		MysqlAdapter.getInstance().createAccount(username, password, Email);
	}

	public boolean isRegisterdMember(Player p) {
		return MysqlAdapter.getInstance().isAccount(p);
	}
}
