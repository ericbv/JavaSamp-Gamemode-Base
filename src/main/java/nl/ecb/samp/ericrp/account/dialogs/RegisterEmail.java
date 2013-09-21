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
import nl.ecb.samp.ericrp.account.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.account.exceptions.playeridAlreadyLoggedInException;

public class RegisterEmail extends AbstractInputDialog {
	private EventManager eventManager;
	private AccountController con;
	private String password;

	public RegisterEmail(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con,
			String password) {
		super(player, shoebill, rootEventManager);
		this.con = con;
		message =  info;
		this.eventManager = new ManagedEventManager(rootEventManager);
		this.password = password;
		eventManager.registerHandler(DialogResponseEvent.class, this,
				dialogEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(DialogCancelEvent.class, this,
				dialogEventHandler, HandlerPriority.NORMAL);
	}

	@Override
	public void show() {
		caption = ("Register dialog [2/2]");
		buttonOk = ("Register");
		buttonCancel = ("Back");
		super.show();
	}

	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
				Player p = event.getPlayer();
				if (event.getInputText().length() < 1) {// TODO add serious
														// requirements
					new RegisterEmail(player, shoebill, rootEventManager,
							"ERROR: Please enter a valid email adres:", con,
							password).show();
				} else {
					try {
						con.register(p, event.getInputText(), p.getName(),
								password);
						con.login(p, p.getName(), password);
					} catch (AccountAlreadyCreatedException e1) {
						p.sendMessage(Color.RED,
								"INTERNAL SERVER ERROR RECONNECT!!");
						p.kick();
					} catch (AccountNotFoundException e) {
						new LoginDialog(p, shoebill, eventManager, password,
								con).show();
					} catch (playeridAlreadyLoggedInException e) {
						// do nothing thing sort themselfs out here
					}
				}
				event.setProcessed();
			} else {
				Player p = event.getPlayer();
				new RegisterPassword(p, shoebill, eventManager,
						"Welcome please enter your desired password:", con)
						.show();
				event.setProcessed();
			}

		}
	};

}
