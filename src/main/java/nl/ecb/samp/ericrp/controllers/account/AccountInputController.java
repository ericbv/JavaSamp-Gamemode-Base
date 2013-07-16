package nl.ecb.samp.ericrp.controllers.account;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.event.player.PlayerConnectEvent;
import net.gtaun.shoebill.event.player.PlayerDisconnectEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.dialog.user.AccountManagerDialog;
import nl.ecb.samp.ericrp.dialog.user.LoginDialog;
import nl.ecb.samp.ericrp.dialog.user.RegisterPassword;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;

public class AccountInputController {
	private Shoebill shoebill;
	private ManagedEventManager eventManager;
	private AccountStore store;
	private AccountController con;

	public AccountInputController(Shoebill shoebill, EventManager rootEventManager)
	{
		this.shoebill = shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
		this.store = AccountStore.getInstance();
		this.con = new AccountController();
		eventManager.registerHandler(PlayerConnectEvent.class, playerEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(PlayerDisconnectEvent.class, playerEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(PlayerCommandEvent.class, playerEventHandler, HandlerPriority.NORMAL);
	}

	public void uninitialize()
	{
		eventManager.cancelAll();
	}

	private PlayerEventHandler playerEventHandler = new PlayerEventHandler()
	{
		@Override
		public void onPlayerConnect(PlayerConnectEvent event)
		{
			Player player = event.getPlayer();
			if(con.isRegisterdMember(player)){
				new LoginDialog(player, shoebill, eventManager, "Password: ",con).show();
			}else{
				new RegisterPassword(player, shoebill, eventManager, "Welcome please enter your desired password:", con).show();
			}

		}

		@Override
		public void onPlayerDisconnect(PlayerDisconnectEvent event)
		{
			try {
				con.logout(event.getPlayer());
			} catch (NotLoggedInException e) {
				Main.logger().info(event.getPlayer().getName()+" Was not logged in nothing has been saved.");
			}
		}

		@Override
		public void onPlayerCommand(PlayerCommandEvent event)
		{
			Player player = event.getPlayer();

			String command = event.getCommand();
			String[] splits = command.split(" ", 2);

			String operation = splits[0].toLowerCase();
			Queue<String> args = new LinkedList<>();

			if (splits.length > 1)
			{
				String[] argsArray = splits[1].split(" ");
				args.addAll(Arrays.asList(argsArray));
			}

			switch (operation)
			{
			case "/account":
				new AccountManagerDialog(player, shoebill, eventManager,con).show();
				event.setProcessed();
				return;
			}
		}
	};
}
