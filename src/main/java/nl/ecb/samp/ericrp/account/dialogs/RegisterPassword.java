package nl.ecb.samp.ericrp.account.dialogs;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import nl.ecb.samp.ericrp.account.controllers.AccountController;

public class RegisterPassword extends AbstractInputDialog {

	private EventManager eventManager;
	private AccountController con;
	private Player p;

	public RegisterPassword(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con) {
		super(player, shoebill, rootEventManager);
		message = info;
		this.p = player;
		this.con = con;
		this.eventManager = new ManagedEventManager(rootEventManager);
	}

	@Override
	public void show() {
		caption = ("Register dialog [1/2]");
		buttonOk = ("Register");
		buttonCancel = ("Quit");
		super.show();
	}

	@Override
	public void onClickOk(String inputText) {
		if (inputText.length() < 1) {// TODO add serious requirements
			new RegisterPassword(player, shoebill, eventManager,
					"ERROR: Please enter a valid password:", con).show();
		} else {
			new RegisterEmail(player, shoebill, eventManager,
					"please enter your Email:", con, inputText).show();
		}
		super.onClickOk(inputText);
	}

	@Override
	protected void onClickCancel() {
		p.sendMessage(Color.BLUE, "Bye!");
		p.kick();
		super.onClickCancel();
	}

}
