package nl.ecb.samp.ericrp.dialog.user;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogCancelEvent;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.controllers.account.AccountController;
import nl.ecb.samp.ericrp.dialog.AbstractInputDialog;
import nl.ecb.samp.ericrp.exceptions.playeridAlreadyLoggedInException;


public class LoginDialog extends AbstractInputDialog {
	private final EventManager eventManager;
	private final AccountController con;
	public LoginDialog(final Player player, final Shoebill shoebill,
			final EventManager rootEventManager, final String info, final AccountController con) {
		super(player, shoebill, rootEventManager, info);
		this.con = con;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class, super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(DialogCancelEvent.class, super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
	}

	@Override
	public void show(){
		this.setCaption("Login");
		this.setButtonCancel("Quit");
		super.show();
	}
	private final DialogEventHandler dialogEventHandler = new DialogEventHandler()
	{
		public void onDialogResponse(final DialogResponseEvent event)
		{	
			final Player p = event.getPlayer();
			try {
				con.login(p, p.getName(), event.getInputText());
			} catch (final AccountNotFoundException e) {
				new LoginDialog(player, shoebill, eventManager, "Wrond password please try again \nPassword: ",con).show();
			} catch (final playeridAlreadyLoggedInException e) {
				p.sendMessage(Color.RED, "ERROR: you are already logged in");
			}
		}

		public void onDialogCancel(final DialogCancelEvent event)
		{
			final Player p = event.getPlayer();
			p.sendMessage(Color.BLUE, "bye!");
			p.kick();
		}
	};



}
