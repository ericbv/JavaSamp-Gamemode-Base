package nl.ecb.samp.ericrp.events;

import nl.ecb.samp.ericrp.model.Account;

public class AccountRequestLogoutEvent extends AccountEvent {

	public AccountRequestLogoutEvent(Account account) {
		super(account);
	}

}
