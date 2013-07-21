package nl.ecb.samp.ericrp.events;

import net.gtaun.util.event.Event;
import nl.ecb.samp.ericrp.model.Account;

public class AccountEvent extends Event {
	private Account account;
	
	
	protected AccountEvent(Account account)
	{
		this.account = account;
	}
	
	public Account getAccount()
	{
		return account;
	}
}
