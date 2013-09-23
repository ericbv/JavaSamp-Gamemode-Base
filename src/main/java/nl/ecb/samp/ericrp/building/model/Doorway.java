package nl.ecb.samp.ericrp.building.model;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerPickupEvent;
import net.gtaun.shoebill.object.Pickup;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.building.exceptions.LockedDoorException;
import nl.ecb.samp.ericrp.main.CharacterStore;
import nl.ecb.samp.ericrp.main.Main;

public class Doorway {
	private AbstractBuilding building;
	private Location locEnter;
	private Location locExit;
	private Pickup entry;

	public Doorway(Location locEnter, Location locExit,
			AbstractBuilding building) {
		this.locEnter = locEnter;
		this.locExit = locExit;
		this.building = building;
		entry = Shoebill.Instance.get().getSampObjectFactory()
				.createPickup(building.getPickupid(), 1, locEnter);
		Shoebill.Instance
				.get()
				.getResourceManager()
				.getGamemode()
				.getEventManager()
				.registerHandler(PlayerPickupEvent.class, playerEventHandler,
						HandlerPriority.NORMAL);
	}

	public AbstractBuilding getBuilding() {
		return building;
	}

	public void setBuilding(AbstractBuilding building) {
		this.building = building;
	}

	public Location getLocEnter() {
		return locEnter;
	}

	public void setLocEnter(Location locEnter) {
		this.locEnter = locEnter;
	}

	public Location getLocExit() {
		return locExit;
	}

	public void setLocExit(Location locExit) {
		this.locExit = locExit;
	}

	public void enter(Player p) throws LockedDoorException {
		if (!p.isInAnyVehicle()) {
			try{
				building.attemptToEnter(p);
				p.setLocation(locExit);
			}catch(LockedDoorException e) {
				throw new LockedDoorException();	
			}
		}
	}

	public void exit(Player p) throws LockedDoorException {
		if (!p.isInAnyVehicle()) {
			try {
				building.attemptToExit(p);
				p.setLocation(locEnter);
			} catch (LockedDoorException e) {
				throw new LockedDoorException();
			}

		}
	}

	private PlayerEventHandler playerEventHandler = new PlayerEventHandler() {
		public void onPlayerPickup(PlayerPickupEvent event) {
			if (event.getPickup().equals(entry)) {
				Player p = event.getPlayer();
				p.sendGameText(2000, 4, building.getGameText());
			}
		}
	};
}
