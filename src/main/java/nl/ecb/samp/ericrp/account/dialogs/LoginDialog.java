package nl.ecb.samp.ericrp.account.dialogs;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogCancelEvent;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.account.controllers.AccountController;
import nl.ecb.samp.ericrp.account.exceptions.playeridAlreadyLoggedInException;

public class LoginDialog extends AbstractInputDialog {
	private EventManager eventManager;
	private AccountController con;

	public LoginDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con) {
		super(player, shoebill, rootEventManager, true);
		this.con = con;
		message = info;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class, this,
				dialogEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(DialogCancelEvent.class, this,
				dialogEventHandler, HandlerPriority.NORMAL);
	}

	@Override
	public void show() {
		caption = ("Login");
		buttonCancel = ("Quit");
		super.show();
	}

	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
				final Player p = event.getPlayer();
				try {
					con.login(p, p.getName(), event.getInputText());
				} catch (final AccountNotFoundException e) {
					new LoginDialog(player, shoebill, eventManager,
							"Wrond password please try again \nPassword: ", con)
							.show();
				} catch (final playeridAlreadyLoggedInException e) {
					p.sendMessage(Color.RED, "ERROR: you are already logged in");
				}
				event.setProcessed();
			} else {
				final Player p = event.getPlayer();
				p.sendMessage(Color.BLUE, "bye!");
				p.kick();
				event.setProcessed();
			}
		}
	};

}
