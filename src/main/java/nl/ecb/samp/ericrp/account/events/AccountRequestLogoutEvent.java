package nl.ecb.samp.ericrp.account.events;

import nl.ecb.samp.ericrp.account.model.Account;

public class AccountRequestLogoutEvent extends AccountEvent {

	public AccountRequestLogoutEvent(Account account) {
		super(account);
	}

}
