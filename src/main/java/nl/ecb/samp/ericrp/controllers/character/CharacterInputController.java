package nl.ecb.samp.ericrp.controllers.character;

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
import nl.ecb.samp.ericrp.dialog.character.selection.CharacterSelectionDialog;
import nl.ecb.samp.ericrp.dialog.user.AccountManagerDialog;
import nl.ecb.samp.ericrp.dialog.user.LoginDialog;
import nl.ecb.samp.ericrp.dialog.user.RegisterPassword;
import nl.ecb.samp.ericrp.events.PlayerLoginEvent;
import nl.ecb.samp.ericrp.events.handler.AccountEventHandler;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.Main;

public class CharacterInputController {
	private Shoebill shoebill;
	private ManagedEventManager eventManager;
	private AccountStore store;
	private CharacterController con;

	public CharacterInputController(Shoebill shoebill, EventManager rootEventManager)
	{
		this.shoebill = shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
		this.store = AccountStore.getInstance();
		this.con = new CharacterController(shoebill,rootEventManager);
		eventManager.registerHandler(PlayerLoginEvent.class, accountEventHandler, HandlerPriority.NORMAL);
	
	}

	public void uninitialize()
	{
		eventManager.cancelAll();
	}

	private AccountEventHandler accountEventHandler = new AccountEventHandler()
	{
		public void onPlayerLogin(PlayerLoginEvent event)
		{
			Player player = event.getPlayer();
			new CharacterSelectionDialog(player, shoebill, eventManager, con).show();
		}
	
	};
}
