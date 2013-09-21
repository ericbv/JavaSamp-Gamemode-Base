package nl.ecb.samp.ericrp.building.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.event.player.PlayerConnectEvent;
import net.gtaun.shoebill.event.player.PlayerDisconnectEvent;
import net.gtaun.shoebill.event.player.PlayerRequestSpawnEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.account.controllers.AccountController;
import nl.ecb.samp.ericrp.account.dialogs.AccountManagerDialog;
import nl.ecb.samp.ericrp.account.dialogs.LoginDialog;
import nl.ecb.samp.ericrp.account.dialogs.RegisterPassword;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.building.model.Doorway;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.BuildingStore;
import nl.ecb.samp.ericrp.main.DoorwayList;
import nl.ecb.samp.ericrp.main.Main;

public class BuildingInputController {
	private Shoebill shoebill;
	private ManagedEventManager eventManager;
	private BuildingStore store;
	private DoorwayList list;
	private BuildingController con;

	public BuildingInputController(Shoebill shoebill,
			EventManager rootEventManager) {
		this.shoebill = shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
		this.store = BuildingStore.getInstance();
		this.list = DoorwayList.getInstance();
		this.con= new BuildingController(shoebill, rootEventManager);
		eventManager.registerHandler(PlayerCommandEvent.class,
				playerEventHandler, HandlerPriority.NORMAL);
	}

	private PlayerEventHandler playerEventHandler = new PlayerEventHandler() {

		@Override
		public void onPlayerCommand(PlayerCommandEvent event) {
			Player p = event.getPlayer();

			String command = event.getCommand();
			String[] splits = command.split(" ", 2);

			String operation = splits[0].toLowerCase();
			Queue<String> args = new LinkedList<>();

			if (splits.length > 1) {
				String[] argsArray = splits[1].split(" ");
				args.addAll(Arrays.asList(argsArray));
			}

			switch (operation) {
			case "/enter": {
				ArrayList<Doorway> entrances = list.getEntrances();
				for (Doorway e : entrances) {
					if (e.getLocEnter().distance(p.getLocation()) < 5) {
						e.enter(p);
					}
				}
				break;
			}
			case "/exit": {
				ArrayList<Doorway> entrances = list.getEntrances();
				for (Doorway e : entrances) {
					if (e.getLocExit().distance(p.getLocation()) < 5) {
						e.exit(p);
					}
				}
				break;
			}

			}
		}
	};
}
