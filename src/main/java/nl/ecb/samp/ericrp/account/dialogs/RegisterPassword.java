package nl.ecb.samp.ericrp.account.dialogs;

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

public class RegisterPassword extends AbstractInputDialog {

	private EventManager eventManager;
	private AccountController con;

	public RegisterPassword(Player player, Shoebill shoebill,
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

	@Override
	public void show() {
		caption = ("Register dialog [1/2]");
		buttonOk = ("Register");
		buttonCancel = ("Quit");
		super.show();
	}

	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
				final Player p = event.getPlayer();
				if (event.getInputText().length() < 1) {// TODO add serious
														// requirements
					new RegisterPassword(player, shoebill, rootEventManager,
							"ERROR: Please enter a valid password:", con)
							.show();
				} else {
					new RegisterEmail(player, shoebill, rootEventManager,
							"please enter your Email:", con,
							event.getInputText()).show();
				}
				event.setProcessed();
			} else {
				Player p = event.getPlayer();
				p.sendMessage(Color.BLUE, "Bye!");
				p.kick();
				event.setProcessed();
			}
		}

	};

}
