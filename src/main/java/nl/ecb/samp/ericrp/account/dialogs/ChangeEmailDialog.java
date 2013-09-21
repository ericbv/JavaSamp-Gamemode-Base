package nl.ecb.samp.ericrp.account.dialogs;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.account.controllers.AccountController;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;

public class ChangeEmailDialog extends AbstractInputDialog {

	private AccountController con;
	private Player p;

	public ChangeEmailDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con) {
		super(player, shoebill, rootEventManager);
		p = player;
		message = info;
		this.con = con;
	}

	public void show() {
		caption = ("Register dialog [2/2]");
		buttonOk = ("Register");
		buttonCancel = ("Back");
		super.show();
	}

	@Override
	public void onClickOk(String inputText) {
		if (inputText.length() < 1) {// TODO add serious requirements
			new ChangeEmailDialog(player, shoebill, rootEventManager,
					"ERROR: Please enter a valid email adres:", con).show();
		} else {
			try {
				AccountStore.getInstance().getAccount(p).setEmail(inputText);
			} catch (NotLoggedInException e) {
				Main.logger()
						.error(p.getName()
								+ " Tried to change email without being logged in");
			}
		}
		super.onClickOk(inputText);
	}

	@Override
	protected void onClickCancel() {
		new RegisterPassword(p, shoebill, eventManager,
				"Welcome please enter your desired password:", con).show();
		super.onClickCancel();
	}

}
