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

public class ChangePassword extends AbstractInputDialog {

	private ManagedEventManager eventManager;
	private AccountController con;

	public ChangePassword(Player player, Shoebill shoebill,
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

	public void show() {
		caption = ("Change password");
		buttonOk = ("Change");
		buttonCancel = ("Cancel");
		super.show();
	}

	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
				final Player p = event.getPlayer();
				if (event.getInputText().length() < 1) {// TODO add serious
														// requirements
					new ChangePassword(player, shoebill, rootEventManager,
							"ERROR: Please enter a valid password:", con)
							.show();
				} else {
					try {
						AccountStore.getInstance().getAccount(p)
								.setPassword(event.getInputText());
					} catch (NotLoggedInException e) {
						Main.logger()
								.error(p.getName()
										+ " Tried to change password without being logged in");
					}
				}
				event.setProcessed();
			} else {
				event.setProcessed();
				destroy();
			}
		}

	};
}
