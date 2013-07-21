package nl.ecb.samp.ericrp.account.events;

import nl.ecb.samp.ericrp.account.model.Account;

public class AccountRequestLoginEvent extends AccountEvent {

	public AccountRequestLoginEvent(Account account) {
		super(account);
	}

}
