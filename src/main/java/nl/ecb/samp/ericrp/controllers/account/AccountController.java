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

	public AccountController()
	{
		this.store = AccountStore.getInstance();
	}
	protected void login(Player p,String username, String password) throws AccountNotFoundException, playeridAlreadyLoggedInException {
		store.setAccount(p, MysqlAdapter.getInstance().getAccount(username,password));
	}
	protected void logout(Player p) throws NotLoggedInException {
		MysqlAdapter.getInstance().saveAccount(store.getAccount(p));
		store.removeAccount(p);
	}
}
