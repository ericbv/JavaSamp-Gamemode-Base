package nl.ecb.samp.ericrp.controllers.account;

import javax.security.auth.login.AccountNotFoundException;


import net.gtaun.shoebill.object.Player;
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
	protected void login(Player p,String username, String password) throws AccountNotFoundException {
		try {
			store.setAccount(p, MysqlAdapter.getInstance().getAccount(username,password));
		} catch (playeridAlreadyLoggedInException e) {
			Main.logger().error("ERROR 001:thing already loaded in store",e);
		}
	}
}
