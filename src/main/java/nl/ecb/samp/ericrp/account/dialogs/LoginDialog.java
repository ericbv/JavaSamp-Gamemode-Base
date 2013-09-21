package nl.ecb.samp.ericrp.account.dialogs;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import nl.ecb.samp.ericrp.account.controllers.AccountController;
import nl.ecb.samp.ericrp.account.exceptions.playeridAlreadyLoggedInException;

public class LoginDialog extends AbstractInputDialog {
	private EventManager eventManager;
	private Player p;
	private AccountController con;

	public LoginDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con) {
		super(player, shoebill, rootEventManager, true);
		this.con = con;
		this.message = info;
		this.p = player;
		this.eventManager = new ManagedEventManager(rootEventManager);

	}

	@Override
	public void show() {
		caption = ("Login");
		buttonCancel = ("Quit");
		super.show();
	}

	@Override
	public void onClickOk(String inputText) {
		try {
			con.login(p, p.getName(), inputText);
		} catch (final AccountNotFoundException e) {
			new LoginDialog(player, shoebill, eventManager,
					"Wrond password please try again \nPassword: ", con).show();
		} catch (final playeridAlreadyLoggedInException e) {
			p.sendMessage(Color.RED, "ERROR: you are already logged in");
		}
		super.onClickOk(inputText);
	}

	@Override
	protected void onClickCancel() {
		p.sendMessage(Color.BLUE, "bye!");
		p.kick();
		super.onClickCancel();
	}

}
