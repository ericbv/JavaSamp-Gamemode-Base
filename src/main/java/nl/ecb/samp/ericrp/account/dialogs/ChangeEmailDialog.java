package nl.ecb.samp.ericrp.account.dialogs;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogCancelEvent;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.account.controllers.AccountController;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;

public class ChangeEmailDialog extends AbstractInputDialog {

	private AccountController con;
	private ManagedEventManager eventManager;

	public ChangeEmailDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con) {
		super(player, shoebill, rootEventManager);
		message = info;
		this.con = con;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class, this,
				dialogEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(DialogCancelEvent.class, this,
				dialogEventHandler, HandlerPriority.NORMAL);
	}

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
					new ChangeEmailDialog(player, shoebill, rootEventManager,
							"ERROR: Please enter a valid email adres:", con)
							.show();
				} else {
					try {
						AccountStore.getInstance().getAccount(p)
								.setEmail(event.getInputText());
					} catch (NotLoggedInException e) {
						Main.logger()
								.error(p.getName()
										+ " Tried to change email without being logged in");
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
