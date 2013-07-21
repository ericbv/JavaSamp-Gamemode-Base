package nl.ecb.samp.ericrp.events;

import nl.ecb.samp.ericrp.model.Account;

public class AccountRequestLoginEvent extends AccountEvent {

	public AccountRequestLoginEvent(Account account) {
		super(account);
	}

}
