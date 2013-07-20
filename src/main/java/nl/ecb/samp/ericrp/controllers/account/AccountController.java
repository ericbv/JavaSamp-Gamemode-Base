package nl.ecb.samp.ericrp.controllers.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;


import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import nl.ecb.samp.ericrp.events.PlayerLoginEvent;
import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playeridAlreadyLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;
import nl.ecb.samp.ericrp.persistance.MysqlAdapter;
import nl.ecb.samp.ericrp.model.Character;
import nl.ecb.samp.ericrp.model.Character.Gender;

public class AccountController {
	private AccountStore store;
	private Shoebill shoebill;
	private ManagedEventManager eventManager;
	
	/**
	 * This is the constructor for the account controller.
	 * @param rootEventManager 
	 * @param shoebill 
	 */
	public AccountController(Shoebill shoebill, EventManager rootEventManager)
	{
		this.store = AccountStore.getInstance();
		this.shoebill =shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
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
		List<Character> characters = new ArrayList<Character>();
		characters.add(Character.load("Eric Bonestroo", 52, new Date(2011, 3, 3), Gender.MALE));
		characters.add(Character.load("Stewie_Holow", 52, new Date(1992, 2, 4),Gender.MALE));
		characters.add(Character.load("Master_Yoda", 52, new Date(1980, 6, 10),Gender.MALE));
		characters.add(Character.load("Klaas_Poep", 52, new Date(1965, 9, 11),Gender.MALE));
		try {
			store.getAccount(p).setCharacters(characters);
		} catch (NotLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eventManager.dispatchEvent(new PlayerLoginEvent(p), p);
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
