package nl.ecb.samp.ericrp.building.model;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerPickupEvent;
import net.gtaun.shoebill.object.Pickup;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.main.CharacterStore;

public class Entrance {
	private AbstractBuilding building;
	private Location locEnter;
	private Location locExit;
	private Pickup entry;

	public Entrance(int pickupid, Location locEnter, Location locExit,
			AbstractBuilding building) {
		this.locEnter = locEnter;
		this.locEnter = locEnter;
		this.building = building;
		entry = Shoebill.Instance.get().getSampObjectFactory().createPickup(pickupid, 1, locEnter);
		Shoebill.Instance.get().getResourceManager().getGamemode().getEventManager().registerHandler(PlayerPickupEvent.class, playerEventHandler, HandlerPriority.NORMAL);
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

	public void enter(Player p) {
		if (!p.isInAnyVehicle()) {
			if (building.attemptToEnter(p)) {
				p.setLocation(locEnter);
				return;
			}
		}
	}

	public void exit(Player p) {
		if (!p.isInAnyVehicle()) {
			if (building.attemptToExit(p)) {
				p.setLocation(locExit);
				return;
			}
		}
	}
	private PlayerEventHandler playerEventHandler = new PlayerEventHandler()
	{
		public void onPlayerPickup(PlayerPickupEvent event){
			if(event.getPickup().equals(entry)){
				Player p = event.getPlayer();
				//TODO add proper cooords
				Shoebill.Instance.get().getSampObjectFactory().createPlayerTextdraw(p, 0, 0, building.getTextdraw());
			}
		}
	};
}
