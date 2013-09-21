package nl.ecb.samp.ericrp.account.dialogs;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.account.controllers.AccountController;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;

public class ChangePassword extends AbstractInputDialog {

	private Player p;
	private AccountController con;

	public ChangePassword(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con) {
		super(player, shoebill, rootEventManager, true);
		p = player;
		this.con = con;
		message = info;

	}

	public void show() {
		caption = ("Change password");
		buttonOk = ("Change");
		buttonCancel = ("Cancel");
		super.show();
	}

	@Override
	public void onClickOk(String inputText) {
		if (inputText.length() < 1) {// TODO add serious requirements
			new ChangePassword(player, shoebill, rootEventManager,
					"ERROR: Please enter a valid password:", con).show();
		} else {
			try {
				AccountStore.getInstance().getAccount(p).setPassword(inputText);
			} catch (NotLoggedInException e) {
				Main.logger()
						.error(p.getName()
								+ " Tried to change password without being logged in");
			}
		}
		super.onClickOk(inputText);
	}

	@Override
	protected void onClickCancel() {
		destroy();
		super.onClickCancel();
	}

}
