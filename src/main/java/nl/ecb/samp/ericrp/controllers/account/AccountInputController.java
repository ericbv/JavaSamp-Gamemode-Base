package nl.ecb.samp.ericrp.controllers.account;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.security.auth.login.AccountNotFoundException;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.event.player.PlayerConnectEvent;
import net.gtaun.shoebill.event.player.PlayerDisconnectEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playeridAlreadyLoggedInException;
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
			//TODO add dialog here

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
			Queue<String> args = new LinkedList<String>();

			if (splits.length > 1)
			{
				String[] argsArray = splits[1].split(" ");
				args.addAll(Arrays.asList(argsArray));
			}

			switch (operation)
			{
			case "/login":
				if (args.size() < 2)
				{
					player.sendMessage(Color.WHITE, "Usage: /login [username] [password]");
					event.setProcessed();
					return;
				}
				try {
					con.login(player,args.poll(),args.poll());
				} catch (AccountNotFoundException e) {
					player.sendMessage(Color.RED, "[ERROR]:invalid username or password");
				} catch (playeridAlreadyLoggedInException e) {
					player.sendMessage(Color.RED, "[ERROR]:allready logged in");
				}
				event.setProcessed();
				return;
			}
		}
	};
}
