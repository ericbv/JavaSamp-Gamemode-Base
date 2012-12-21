package nl.ecb.samp.ericrp.dialog.user;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.constant.DialogStyle;
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
	private EventManager eventManager;
	private AccountController con;
	public LoginDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, AccountController con) {
		super(player, shoebill, rootEventManager, info);
		this.con = con;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class, super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(DialogCancelEvent.class, super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
	}
	@Override
	public void show(){
		this.setCaption("Login");
		super.show();
	}
	private DialogEventHandler dialogEventHandler = new DialogEventHandler()
	{
		public void onDialogResponse(DialogResponseEvent event)
		{	
			Player p = event.getPlayer();
			try {
				con.login(p, p.getName(), event.getInputText());
			} catch (AccountNotFoundException e) {
				new LoginDialog(player, shoebill, eventManager, "Wrond password please try again \nPassword: ",con).show();
			} catch (playeridAlreadyLoggedInException e) {
				p.sendMessage(Color.RED, "ERROR: you are already logged in");
			}
		}

		public void onDialogCancel(DialogCancelEvent event)
		{
			Player p = event.getPlayer();
			new LoginDialog(p, shoebill, eventManager, "Password: ",con).show();
		}
	};



}
