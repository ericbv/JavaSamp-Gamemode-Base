package nl.ecb.samp.ericrp.main;

import java.util.HashMap;
import java.util.Map;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.account.exceptions.playeridAlreadyLoggedInException;
import nl.ecb.samp.ericrp.account.model.Account;

public class AccountStore {
	private Map<Player,Account> accountList;
	private static AccountStore INSTANCE;
	public AccountStore(){
		accountList = new HashMap<Player, Account>();
	}
	public static AccountStore getInstance(){
		if(INSTANCE == null){
			INSTANCE = new AccountStore();
		}
		return INSTANCE;
	}

	public boolean isLoggedIn(Player p){
		if(accountList.get(p) == null){
			return false;
		}
		else{ 
			return true;
		}
	}

	public Account getAccount(Player p) throws NotLoggedInException{
		if(!isLoggedIn(p)){
			throw new NotLoggedInException();
		}
		return accountList.get(p);
		

	}
	public void setAccount(Player p, Account account) throws playeridAlreadyLoggedInException{
		if(!isLoggedIn(p) == false){
			throw new playeridAlreadyLoggedInException();
		}
		accountList.put(p, account);
	}
	public void removeAccount(Player p){
		accountList.remove(p);
	}
}
