package nl.ecb.samp.ericrp.account.dialogs;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.account.controllers.AccountController;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog;
import nl.ecb.samp.ericrp.main.AccountStore;

public class AccountManagerDialog extends AbstractListDialog {

	private AccountController con;
	private Player player;
	public AccountManagerDialog(Player player, Shoebill shoebill,
			EventManager eventManager, AccountController con) {
		super(player, shoebill, eventManager);
		setCaption("Account Manager Dialog");
		this.con = con;
		this.player = player;
	}
	@Override
	public void show()
	{
		if(AccountStore.getInstance().isLoggedIn(player)){
			dialogListItems.add(
					new DialogListItem("Change Password")
					{
						@Override
						public void onItemSelect()
						{
							new ChangePassword(player,shoebill, rootEventManager, "Please enter your new password:", con).show();
							destroy();
						}
					});
			dialogListItems.add(
					new DialogListItem("Change Email")
					{
						@Override
						public void onItemSelect()
						{
							new ChangePassword(player,shoebill, rootEventManager, "Please enter your new emailadres:", con).show();
							destroy();
						}
					});
		}
		else{

			if (!con.isRegisterdMember(player)) 
				dialogListItems.add(
						new DialogListItem("Register")
						{
							@Override
							public void onItemSelect()
							{
								new RegisterPassword(player, shoebill, rootEventManager, "Please enter your desired password:", con).show();
								destroy();
							}
						});
			if (con.isRegisterdMember(player)) 
				dialogListItems.add(
						new DialogListItem("Login")
						{
							@Override
							public void onItemSelect()
							{
								new LoginDialog(player, shoebill, rootEventManager, "Please enter your password", con);
								destroy();
							}
						});
		}

		super.show();
	}
}
