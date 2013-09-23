package nl.ecb.samp.ericrp.building.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.building.exceptions.NotLockableException;
import nl.ecb.samp.ericrp.building.exceptions.NotNearDoorException;
import nl.ecb.samp.ericrp.building.interfaces.LockableBuilding;
import nl.ecb.samp.ericrp.building.model.AbstractBuilding;
import nl.ecb.samp.ericrp.building.model.Doorway;
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
		this.con = new BuildingController(shoebill, rootEventManager, list);
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
			case "/lock": {
				try {
					con.lock(p, con.getDoorway(p).getBuilding());
					p.sendMessage(Color.GREEN, "Door has been Locked");
				} catch (NotLockableException e1) {
					p.sendMessage(Color.RED,
							"[ERROR]This building cannot be locked");
				} catch (NotNearDoorException e1) {
					p.sendMessage(Color.RED, "[ERROR]You're not near a door");
				}
				break;
			}
			case "/unlock": {
				try {
					con.unLock(p, con.getDoorway(p).getBuilding());
					p.sendMessage(Color.GREEN, "Door has been Locked");
				} catch (NotLockableException e1) {
					p.sendMessage(Color.RED,
							"[ERROR]This building cannot be locked");
				} catch (NotNearDoorException e1) {
					p.sendMessage(Color.RED, "[ERROR]You're not near a door");
				}
				break;
			}

			}
		}
	};

	public void uninitialize() {
		eventManager.cancelAll();
	}
}
