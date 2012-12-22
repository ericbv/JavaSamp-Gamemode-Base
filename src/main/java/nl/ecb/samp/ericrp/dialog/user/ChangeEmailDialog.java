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
import nl.ecb.samp.ericrp.exceptions.AccountAlreadyCreatedException;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playeridAlreadyLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;

public class ChangeEmailDialog extends AbstractInputDialog {

	private AccountController con;
	private ManagedEventManager eventManager;
	public ChangeEmailDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info,AccountController con) {
		super(player, shoebill, rootEventManager, info);
		this.con = con;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class, super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(DialogCancelEvent.class, super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
	}
	public void show(){
		this.setCaption("Register dialog [2/2]");
		this.setButtonOk("Register");
		this.setButtonCancel("Back");
		super.show();
	}
	private DialogEventHandler dialogEventHandler = new DialogEventHandler()
	{
		public void onDialogResponse(DialogResponseEvent event)
		{	
			if(event.getDialogResponse() == 1){
				Player p = event.getPlayer();
				if(event.getInputText().length() < 1){//TODO add serious requirements
					new ChangeEmailDialog(player,shoebill, rootEventManager, "ERROR: Please enter a valid email adres:", con).show();
				}else{
					try {
						AccountStore.getInstance().getAccount(p).setEmail(event.getInputText());
					} catch (NotLoggedInException e) {
						Main.logger().error(p.getName()+" Tried to change email without being logged in");
					}
				}
				event.setProcessed();
			}else{
				Player p = event.getPlayer();
				new RegisterPassword(player, shoebill, eventManager, "Welcome please enter your desired password:", con).show();
				event.setProcessed();
			}

		}
	};

}
