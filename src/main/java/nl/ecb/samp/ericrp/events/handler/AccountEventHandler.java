package nl.ecb.samp.ericrp.events.handler;

import net.gtaun.util.event.AbstractEventHandler;
import nl.ecb.samp.ericrp.events.AccountRequestLoginEvent;
import nl.ecb.samp.ericrp.events.AccountRequestLogoutEvent;
import nl.ecb.samp.ericrp.events.PlayerLoginEvent;

public class AccountEventHandler extends AbstractEventHandler {

	protected AccountEventHandler() {
		super(AccountEventHandler.class);
	}

	protected void onPlayerLogin(PlayerLoginEvent event) 					{}
	protected void onAccountRequestLogin(AccountRequestLoginEvent event) 	{}
	protected void onAccountRequestLogout(AccountRequestLogoutEvent event) 	{}
}
